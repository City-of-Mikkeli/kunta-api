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

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;

public abstract class AbstractIntegrationTest extends AbstractTest {

  public class AbstractMocker {
    
    private List<StringGetMock> stringMocks;
    private List<BinaryGetMock> binaryMocks;
    
    public AbstractMocker() {
      stringMocks = new ArrayList<>();
      binaryMocks = new ArrayList<>();
    }
    
    public void mockGetBinary(String path, String type, String binaryFile) {
      try (InputStream binaryStream = getClass().getClassLoader().getResourceAsStream(binaryFile)) {
        binaryMocks.add(new BinaryGetMock(path, type, IOUtils.toByteArray(binaryStream)));
      } catch (IOException e) {
        fail(e.getMessage());
      }
    }
    
    public void mockGetString(String path, String type, String content) {
      stringMocks.add(new StringGetMock(path, type, content));
    }
    
    public void mockGetJSON(String path, Object object) {
      try {
        stringMocks.add(new StringGetMock(path, "application/json", new ObjectMapper().writeValueAsString(object)));
      } catch (JsonProcessingException e) {
        fail(e.getMessage());
      }
    }
    
    public void startMock() {
      for (StringGetMock stringMock : stringMocks) {
        createStringMock(stringMock.getPath(), stringMock.getType(), stringMock.getContent());
      }
      
      for (BinaryGetMock binaryMock : binaryMocks) {
        createBinaryMock(binaryMock.getPath(), binaryMock.getType(), binaryMock.getContent());
      }
    }

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
    
    public class StringGetMock {

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
    
    public class BinaryGetMock {

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
