<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );
  
  function kunta_api_get_service_id($object, $field_name, $request) {
  	return get_post_meta( $object[ 'id' ], 'kunta-api-service-id', true);
  }
  
  add_action('rest_api_init', function () {
  	register_rest_field('page', 'kunta-api-service-id', array(
  	  'get_callback'    => 'kunta_api_get_service_id',
  	  'update_callback' => null,
  	  'schema'          => array(
  	  	 "title" => "Kunta API service id",
  	  	 "properties" => array(
  	  	 	"kunta-api-service-id" => array (
  	  	 	  "type" => "string"
  	  	 	)
  	  	 )
  	  )
  	));
  });
  
?>