<?php
/*
  Created on Aug 9, 2016
  Plugin Name: Kunta API Management
  Description: Kunta API Management plugin for Wordpress
  Version: 0.1
  Author: Antti Leppä / Otavan Opisto
*/

defined( 'ABSPATH' ) || die( 'No script kiddies please!' );

require_once 'kunta-api-settings.php';
require_once 'kunta-api-updater.php';
require_once 'embeds.php';
require_once 'kunta-api-tinymce.php';
require_once 'kunta-api-rest.php';

function kunta_api_management_page_categories() {
  register_taxonomy_for_object_type( 'category', 'page' );
}

function kunta_api_management_activate() {
};

load_plugin_textdomain('kunta_api_management', WP_PLUGIN_URL . '/kunta_api_management/langs/', 'kunta_api_management/langs/');
register_activation_hook( __FILE__, 'kunta_api_management_activate' );
add_action( 'init', 'kunta_api_management_page_categories' );

?>