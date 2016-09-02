package fi.otavanopisto.kuntaapi.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;

/**
 * Abstract base class for integration tests
 * 
 * @author Antti Leppä
 */
public abstract class AbstractIntegrationTest extends AbstractTest {

  private static Logger logger = Logger.getLogger(AbstractTest.class.getName());
  
  /**
   * Abstract base class for all mockers
   * 
   * @author Antti Leppä
   */
  public class AbstractMocker {
    
    private List<StringGetMock> stringMocks;
    private List<BinaryGetMock> binaryMocks;
    
    /**
     * Constructor
     */
    public AbstractMocker() {
      stringMocks = new ArrayList<>();
      binaryMocks = new ArrayList<>();
    }
    
    /**
     * Mocks binary response for GET request on path
     * 
     * @param path path
     * @param type response content type 
     * @param binaryFile path of mocked file
     */
    public void mockGetBinary(String path, String type, String binaryFile) {
      try (InputStream binaryStream = getClass().getClassLoader().getResourceAsStream(binaryFile)) {
        binaryMocks.add(new BinaryGetMock(path, type, IOUtils.toByteArray(binaryStream)));
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Failed to read mock binary file", e);
        fail(e.getMessage());
      }
    }
    
    /**
     * Mocks string response for GET request on path
     * 
     * @param path path
     * @param type response content type 
     * @param content response content
     */
    public void mockGetString(String path, String type, String content) {
      stringMocks.add(new StringGetMock(path, type, content));
    }
    
    /**
     * Mocks JSON response for GET request on path
     * 
     * @param path path
     * @param object JSON object
     */
    public void mockGetJSON(String path, Object object) {
      try {
        stringMocks.add(new StringGetMock(path, "application/json", new ObjectMapper().writeValueAsString(object)));
      } catch (JsonProcessingException e) {
        logger.log(Level.SEVERE, "Failed to serialize mock JSON object", e);
        fail(e.getMessage());
      }
    }
    
    /**
     * Starts mocking requests
     */
    public void startMock() {
      for (StringGetMock stringMock : stringMocks) {
        createStringMock(stringMock.getPath(), stringMock.getType(), stringMock.getContent());
      }
      
      for (BinaryGetMock binaryMock : binaryMocks) {
        createBinaryMock(binaryMock.getPath(), binaryMock.getType(), binaryMock.getContent());
      }
    }

    /**
     * Ends mocking
     */
    public void endMock() {
      WireMock.reset();
    }
    
    private void createBinaryMock(String path, String type, byte[] binary) {
      stubFor(get(urlEqualTo(path))
        .willReturn(aResponse()
        .withHeader("Content-Type", type)
        .withBody(binary)));
    }
    
    private void createStringMock(String path, String type, String content) {
      stubFor(get(urlEqualTo(path))
          .willReturn(aResponse()
          .withHeader("Content-Type", type)
          .withBody(content)));
    }
    
    private class StringGetMock {

      private String path;
      private String type;
      private String content;
     
      public StringGetMock(String path, String type, String content) {
        this.path = path;
        this.type = type;
        this.content = content;
      }
      
      public String getPath() {
        return path;
      }
     
      public String getContent() {
        return content;
      }
      
      public String getType() {
        return type;
      }
    }
    
    private class BinaryGetMock {

      private String path;
      private String type;
      private byte[] content;
     
      public BinaryGetMock(String path, String type, byte[] content) {
        this.path = path;
        this.type = type;
        this.content = content;
      }
      
      public String getPath() {
        return path;
      }
     
      public byte[] getContent() {
        return content;
      }
      
      public String getType() {
        return type;
      }
    }

  }
  
}
