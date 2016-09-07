<?php
  defined( 'ABSPATH' ) || die( 'No script kiddies please!' );
  
  function kunta_api_get_data($object, $field_name, $request) {
  	return array(
  	  "service-id" => get_post_meta( $object[ 'id' ], 'kunta-api-service-id', true)
  	);
  }
  
  add_action('rest_api_init', function () {
  	register_rest_field('page', 'kunta-api-data', array(
  	  'get_callback'    => 'kunta_api_get_data',
  	  'update_callback' => null,
  	  'schema'          => array(
  	  	 "title" => "Kunta API Data",
  	  	 "properties" => array(
  	  	 	"service-id" => array (
  	  	 	  "type" => "string"
  	  	 	)
  	  	 )
  	  )
  	));
  });
  
?>