<?php
  defined( 'ABSPATH' ) || die( 'No script kiddies please!' );
  require_once('vendor/autoload.php');
  
  function getKuntaApiClient() {
  	$options = get_option('kunta_api_management');
  	 
  	if (!$options['apibaseurl']) {
  		wp_die( __( 'Missing apibaseurl setting from Kunta API Settings', 'kunta_api_management' ) );
  	}
  		
  	$apibaseurl = $options['apibaseurl'];
  	$api_config = KuntaAPI\Configuration::getDefaultConfiguration();
  	$api_config->setHost($apibaseurl);
  	
  	return new KuntaAPI\ApiClient($api_config);
  }  
  
?>