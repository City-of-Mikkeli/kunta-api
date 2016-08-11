<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );

  require_once ('vendor/autoload.php');
  require_once ('kunta-api-client.php');
  
  use Sunra\PhpSimple\HtmlDomParser;
  
  if (is_admin()) {
  	add_filter('content_edit_pre', 'kunta_api_setup_embeds');
  }
  
  function kunta_api_get_description($descriptions, $language, $type) {
  	foreach ($descriptions as $description) {
  	  if ($description->getLanguage() == $language && $type == $description->getType()) {
  	  	return $description->getValue();
  	  }
  	}
  	
  	return null;
  }
  
  function kunta_api_load_embeded_content($service_id) {
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
  	
  	return 'Failed to load service data';
  }
  
  function kunta_api_setup_embeds($content) {
  	$dom = HtmlDomParser::str_get_html($content);
  	
  	foreach ($dom->find('article[data-type="kunta-api-embedded-service"]') as $article) {
  	  $serviceId = $article->{'data-service-id'};
  	  $article->innertext = kunta_api_load_embeded_content($serviceId);
  	  $article->class = 'mceNonEditable';
  	}
  	
  	return $dom;
  }
  
?>