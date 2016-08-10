<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );
  
  require_once ('vendor/autoload.php');
  require_once ('kunta-api-client.php');
  require_once (ABSPATH . 'wp-admin/includes/taxonomy.php');
  
  function kunta_api_find_term_by_service_class_id($service_class_id) {
  	global $wpdb;
  	
  	$results = $wpdb->get_results("SELECT term_id as terms FROM $wpdb->termmeta WHERE meta_key = 'kunta_api_service_class_id' and meta_value = '$service_class_id'");
  	if (!empty($results)) {
      return intval($results->term_id);
  	}

  	return null;
  }
  
  function kunta_api_update_service_class($service_class_id, $class_name) {
  	$term_id = kunta_api_find_term_by_service_class_id($service_class_id);
  	if ($term_id == null) {
  	  wp_create_category($class_name);
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
  
  	wp_schedule_single_event(time() + 1, 'kunta_api_update_service_classes' );
  }
  
  add_action('kunta_api_update_service_classes', 'kunta_api_update_service_classes' );
  wp_schedule_single_event(time() + 1, 'kunta_api_update_service_classes' );
  
?>