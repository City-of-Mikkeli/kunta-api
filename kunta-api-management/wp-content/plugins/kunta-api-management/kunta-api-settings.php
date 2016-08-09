<?php
  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );

  if (is_admin()) {
    add_action ( 'admin_menu', 'kunta_api_management_settings_menu' );
    add_action ( 'admin_init', 'kunta_api_management_register_settings' );
  }
  
  function kunta_api_management_settings_menu() {
	add_options_page ( __( "Kunta API", 'kunta_api_management' ), __ ( "Kunta API", 'kunta_api_management' ), 'manage_options', 'kunta_api_management_settings', 'kunta_api_management_settings_page' );
  }
  
  function kunta_api_management_validate($input) {
  	return $input;
  }
  
  function kunta_api_management_section_text() {
  	echo  __( "Kunta API Management Settings", 'kunta_api_management');
  }
  
  function kunta_api_management_apibaseurl() {
  	$options = get_option('kunta_api_management');
  	echo "<input id='apibaseurl' name='kunta_api_management[apibaseurl]' size='40' type='text' value='{$options['apibaseurl']}' />";
  }
  
  function kunta_api_management_organizationid() {
  	$options = get_option('kunta_api_management');
  	echo "<input id='organizationid' name='kunta_api_management[organizationid]' size='40' type='text' value='{$options['organizationid']}' />";
  }
  
  function kunta_api_management_register_settings() { 
  	register_setting( 'kunta_api_management', 'kunta_api_management', 'kunta_api_management_validate');
  	add_settings_section('kunta_api_management', 'Settings', 'kunta_api_management_section_text', 'kunta_api_management');
  	add_settings_field('apibaseurl', 'API Base URL', 'kunta_api_management_apibaseurl', 'kunta_api_management', 'kunta_api_management');
  	add_settings_field('organizationid', 'Organization Id', 'kunta_api_management_organizationid', 'kunta_api_management', 'kunta_api_management');
  }
  
  function kunta_api_management_settings_page() {
  	if (!current_user_can('manage_options')) {
  	  wp_die( __( 'You do not have sufficient permissions to access this page.' ) );
  	}
	
	echo '<div class="wrap">';
	echo "<h2>" . __( "Kunta API Management Settings", 'kunta_api_management' ) . "</h2>";
	echo '<form action="options.php" method="POST">';
	
	settings_fields( 'kunta_api_management' );
	do_settings_sections('kunta_api_management');
	submit_button();
	
	echo "</form>";
	echo "</div>";
}

?>