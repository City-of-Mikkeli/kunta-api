<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );

  require_once ('vendor/autoload.php');
  require_once ('kunta-api-client.php');
  
  use Sunra\PhpSimple\HtmlDomParser;
  
  if (is_admin()) {
  	add_filter('content_edit_pre', 'kunta_api_content_edit_pre');
  }
  
  add_filter('the_content', 'kunta_api_content_filter');
  
  function kunta_api_get_description($descriptions, $language, $type) {
  	foreach ($descriptions as $description) {
  	  if ($description->getLanguage() == $language && $type == $description->getType()) {
  	  	return $description->getValue();
  	  }
  	}
  	
  	return null;
  }
  
  function kunta_api_load_embeded_content($service_id) {
  	try {
	  $options = get_option('kunta_api_management');
	  
	  if (!$options['organizationid']) {
	    error_log(__( 'Failed to update service classes: Missing organization id setting from Kunta API Settings', 'kunta_api_management' ));
	  } else {
	    $organization_id = $options['organizationid'];
	  	$servicesApi = new KuntaAPI\Api\ServicesApi(getKuntaApiClient());
	  	$service = $servicesApi->findService($organization_id, $service_id);
	  	$content = kunta_api_get_description($service->getDescriptions(), "fi", "Description");
	  	  	
	  	if (!empty($content)) {
	  	  return $content;
	  	}
	  } 
  	} catch (Exception $e) {
  	  error_log("Loading embedded content crashed with message: [" . $e->getCode() . "] " . $e->getMessage());
	}
  	
  	return null;
  }
  
  function kunta_api_content_edit_pre($content) {
  	$dom = HtmlDomParser::str_get_html($content);
  	
  	foreach ($dom->find('*[data-type="kunta-api-embedded-service"]') as $article) {
  	  $serviceId = $article->{'data-service-id'};
  	  $article->class = 'mceNonEditable';

  	  $content = kunta_api_load_embeded_content($serviceId);;
  	  
  	  if (!empty($content)) {
  	    $article->innertext = $content;
  	  } else {
  	  	if (empty($article->innertext)) {
  	  	  $article->innertext = __( 'Failed to load embedded content', 'kunta_api_management');
  	  	}
  	  }
  	}

  	return $dom;
  }
  
  function kunta_api_content_filter($content) {
  	$dom = HtmlDomParser::str_get_html($content);
  	
  	foreach ($dom->find('*[data-type="kunta-api-embedded-service"]') as $article) {
  	  $serviceId = $article->{'data-service-id'};
  	  $article->class = 'embedded';
  	  $article->{'data-service-id'} = null;
  	  $article->{'data-type'} = null;
  	  	
  	  $content = kunta_api_load_embeded_content($serviceId);
  	  
  	  if (!empty($content)) {
  	    $article->innertext = $content;
  	  } else {
  	  	if (empty($article->innertext)) {
  	  	  $article->innertext = __( 'Failed to load embedded content', 'kunta_api_management');
  	  	}
  	  }
  	}

  	return $dom;
  }
  
?>