<?php
  defined( 'ABSPATH' ) || die( 'No script kiddies please!' );

  require_once ('vendor/autoload.php');
  require_once ('kunta-api-client.php');
  require_once ('service-loader.php');
  
  use Sunra\PhpSimple\HtmlDomParser;
  
  if (is_admin()) {
  	add_filter('content_edit_pre', 'kunta_api_setup_embeds');
  }

  function kunta_api_setup_embeds($content) {
    $serviceLoader = new ServiceLoader();
  	$dom = HtmlDomParser::str_get_html($content);
    
  	foreach ($dom->find('*[data-type="kunta-api-embedded-data"]') as $article) {
      $serviceId = $article->{'data-service-id'};
      $serviceComponent = $article->{'data-service-component'};
      $article->class = 'mceNonEditable';
      $content = $serviceLoader->loadServiceComponent($serviceId, $serviceComponent);

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