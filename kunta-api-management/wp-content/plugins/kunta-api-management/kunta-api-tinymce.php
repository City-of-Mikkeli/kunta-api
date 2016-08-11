<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );
  require_once('vendor/autoload.php');
  
  if (is_admin()) {
  	add_action('init', 'setup_tinymce_plugins');
  }
  
  function add_tinymce_plugins($plugin_array) {
  	$plugin_array['noneditable'] = plugin_dir_url( __FILE__ ) . 'tinymce/noneditable/plugin.min.js';
  	return $plugin_array;
  }
  
  function setup_tinymce_plugins() {
  	add_filter( 'mce_external_plugins', 'add_tinymce_plugins' );
  }
  
?>