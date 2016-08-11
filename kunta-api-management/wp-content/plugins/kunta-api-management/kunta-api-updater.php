<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );
  
  require_once ('vendor/autoload.php');
  require_once ('kunta-api-client.php');
  require_once (ABSPATH . 'wp-admin/includes/taxonomy.php');
  require_once (ABSPATH . 'wp-includes/pluggable.php');
  
  // TODO: Prevent sql injection
  
  $GLOBALS['wp_rewrite'] = new WP_Rewrite();
  
  function kunta_api_find_term_by_service_class_id($service_class_id) {
  	global $wpdb;
  	
  	$results = $wpdb->get_results("SELECT term_id FROM $wpdb->termmeta WHERE meta_key = 'kunta-api-service-class-id' and meta_value = '$service_class_id'");
  	if (!empty($results)) {
      return intval($results[0]->term_id);
  	}

  	return null;
  }
  
  function kunta_api_find_service_class_ids($index) {
  	global $wpdb;
  	$result = $wpdb->get_results("SELECT meta_value FROM $wpdb->termmeta WHERE meta_key = 'kunta-api-service-class-id' limit 1 offset $index");
    
  	if (!empty($result)) {
  	  return $result[0]->meta_value;
  	}
  	
  	return null;
  }
  
  function kunta_api_list_service_class_ids() {
  	$ids = array();
  	
  	global $wpdb;
  	$results = $wpdb->get_results("SELECT meta_value FROM $wpdb->termmeta WHERE meta_key = 'kunta-api-service-class-id'");
  
  	if (!empty($results)) {
  	  foreach ($results as $result) {
  	  	$ids[] = $result->meta_value;
  	  }
  	  
  	}
  	 
  	return $ids;
  }
  
  function kunta_api_find_page_id_by_service_id($service_id) {
  	global $wpdb;
  	
  	$result = $wpdb->get_results("SELECT post_id FROM $wpdb->postmeta WHERE meta_key = 'kunta-api-service-id' and meta_value = '$service_id'");
  	
  	if (!empty($result)) {
  		return $result[0]->post_id;
  	}
  	 
  	return null;
  }

  function kunta_api_attach_page_service_id($page_id, $service_id) {
  	add_post_meta($page_id, 'kunta-api-service-id', $service_id, true);
  }
  
  function kunta_api_attach_service_class_to_page($page_id, $service_class_id) {
  	$term_id = kunta_api_find_term_by_service_class_id($service_class_id);
  	if (!empty($term_id)) {
  	  wp_set_post_categories($page_id, array($term_id), true);
  	}
  }

  function kunta_api_update_service_class($service_class_id, $class_name) {
  	$term_id = kunta_api_find_term_by_service_class_id($service_class_id);
  	if (empty($term_id)) {
  	  $term_id = wp_create_category($class_name);
      add_term_meta($term_id, 'kunta-api-service-class-id', $service_class_id, true);
  	}
  }
  
  function kunta_api_update_service_classes() {
  	$options = get_option('kunta_api_management');
  	 
  	if (!$options['organizationid']) {
  	  error_log(__( 'Failed to update service classes: Missing organization id setting from Kunta API Settings', 'kunta_api_management' ));
  	} else {
	  $organization_id = $options['organizationid'];
	  
	  try {
	    $servicesApi = new KuntaAPI\Api\ServiceCategoriesApi(getKuntaApiClient());
	  	$serviceClasses = $servicesApi->listServiceClasses($organization_id);
	  	
	  	foreach ($serviceClasses as $serviceClass) {
	  	  $service_class_id = $serviceClass['id'];
	      $class_name = $serviceClass['name'];
	      kunta_api_update_service_class($service_class_id, $class_name);
	  	}
	  	
	  } catch (Exception $e) {
	    error_log($e);
	  }
  	}
  }
  
  function kunta_api_localized_string($localized_value) {
  	$result = '';
  	
  	foreach ($localized_value as $item) {
  	  $language = $item['language'];
  	  $value = $item['value'];
  	  $result = $result . '[:' . $language . ']' . $value; 
  	}
  	
  	return $result . '[:]';
  }

  function kunta_api_update_services($service_class_id) {
  	$options = get_option('kunta_api_management');
  	
  	if (!$options['organizationid']) {
  		error_log(__( 'Failed to update service classes: Missing organization id setting from Kunta API Settings', 'kunta_api_management' ));
  	} else {
  	  $organization_id = $options['organizationid'];
  	  try {
  	  	$servicesApi = new KuntaAPI\Api\ServicesApi(getKuntaApiClient());
  		$services = $servicesApi->listServices($organization_id, $service_class_id);
  		 
  		foreach ($services as $service) {
  		  // TODO: User id to settings
  		  $id = $service->getId();
  		  $user_id = 1;
  		  $name = kunta_api_localized_string($service->getName());
  		  $class_ids = $service->getClassIds();
		  $content = '<article data-type="kunta-api-embedded-service" data-service-id="' . $id .'">' . __( 'Loading please wait...', 'kunta_api_management' ) . '</article>';
          $status = "publish"; 
		  
		  if (!empty($name)) {
		  	$post_id = kunta_api_find_page_id_by_service_id($id);
		  	if (!isset($post_id)) {
			  $page_id = wp_insert_post(array(
			    'post_content' => $content,
				'post_title' => $name,
				'post_status' => $status,
				'post_type' => 'page'
		      ));
			  
			  if ($page_id) {
			    kunta_api_attach_page_service_id($page_id, $id);
			  	foreach ($class_ids as $class_id) {
			  	  kunta_api_attach_service_class_to_page($page_id, $class_id);
			  	}
			  }		  		
		  	}
		  }
  		}

  	  } catch (Exception $e) {
  		error_log($e);
  	  }
  	}
  }
  
  kunta_api_update_service_classes();
  foreach (kunta_api_list_service_class_ids() as $service_class_id) {
  	kunta_api_update_services($service_class_id);
  }
  
?>