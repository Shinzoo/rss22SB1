package fr.univrouen.rss22.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

public class TestRSS {
	

	public String loadFileXML() throws IOException {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:xml/item.xml");
		return asString(resource);
	}
	
	public String asString(Resource resource) throws IOException  {
		
		try (Reader reader = new InputStreamReader(resource.getInputStream())) 
		{
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/*public String loadFileXML() {
		
		InputStream inputStream = resource.getInputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer;

	    resource = new DefaultResourceLoader().getResource("classpath:xml/item.xml");
	    String string ="";

	    try {
	        transformer = tf.newTransformer();	       
	        StringWriter writer = new StringWriter();

	        transformer.transform(new DOMSource((Node) resource), new StreamResult(writer));

	        String xmlString = writer.getBuffer().toString(); 
	        //System.out.println(xmlString);   
	        string = xmlString;
	    } 
	    catch (TransformerException e) {e.printStackTrace();}
	    catch (Exception e){ e.printStackTrace();}	

	    return string;
	}*/
	}
