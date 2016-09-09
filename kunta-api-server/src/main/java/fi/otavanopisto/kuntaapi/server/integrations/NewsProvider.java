package fi.otavanopisto.kuntaapi.server.integrations;

import java.time.OffsetDateTime;
import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.NewsArticle;

/**
 * Interafce that describes a single news provider
 * 
 * @author Antti Leppä
 */
public interface NewsProvider {

  /**
   * List news in an organization
   * 
   * @param organizationId organization id
   * @param publishedBefore return only news published before the date
   * @param publishedAfter return only news published after the date
   * @param firstResult first index of results
   * @param maxResults maximum number of results
   * @return organization news articles
   */
  public List<NewsArticle> listOrganizationNews(OrganizationId organizationId, OffsetDateTime publishedBefore, OffsetDateTime publishedAfter, 
      Integer firstResult, Integer maxResults);
  
  /**
   * Finds a single news article
   * 
   * @param organizationId organization id
   * @param newsArticleId news article id
   * @return news article or null of not found
   */
  public NewsArticle findOrganizationNewsArticle(OrganizationId organizationId, NewsArticleId newsArticleId);

  /**
   * Lists images attached to the news article
   * 
   * @param organizationId organization id
   * @param newsArticleId news article id
   * @return list of images attached to the news article
   */
  public List<Attachment> listNewsArticleImages(OrganizationId organizationId, NewsArticleId newsArticleId);
  
  /**
   * Finds a news article image
   * 
   * @param organizationId organization id
   * @param newsArticleId news article id
   * @param attachmentId image id
   * @return an news article image or null if not found
   */
  public Attachment findNewsArticleImage(OrganizationId organizationId, NewsArticleId newsArticleId, AttachmentId attachmentId);
  
  /**
   * Returns data of news article image
   * 
   * @param organizationId organization id
   * @param newsArticleId news article id
   * @param attachmentId image id
   * @param size max size of image. Specify null for untouched
   * @return news article image data
   */
  public AttachmentData getNewsArticleImageData(OrganizationId organizationId, NewsArticleId newsArticleId, AttachmentId attachmentId, Integer size);
  
}
