<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );
  require_once('vendor/autoload.php');
  
  if (is_admin()) {
  	add_action('init', 'kunta_api_tinymce_plugins');
  	add_action('init', 'kunta_api_tinymce_styles' );
  }
  
  function kunta_api_tinymce_setup_plugins($plugin_array) {
    // TODO: Automatize tinymce version updating...
  	$plugin_array['noneditable'] = '//cdnjs.cloudflare.com/ajax/libs/tinymce/4.3.10/plugins/noneditable/plugin.min.js';
  	return $plugin_array;
  }
  
  function kunta_api_tinymce_plugins() {
  	add_filter( 'mce_external_plugins', 'kunta_api_tinymce_setup_plugins' );
  }
  
  function kunta_api_tinymce_styles() {
  	add_editor_style(plugin_dir_url( __FILE__ ) . 'tinymce/kunta-api-styles.css');
  }
  
?>