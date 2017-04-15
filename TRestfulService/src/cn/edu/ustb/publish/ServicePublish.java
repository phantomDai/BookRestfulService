package cn.edu.ustb.publish;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import cn.edu.ustb.impl.*;


public class ServicePublish {
	public static void main(String[] args) {
		JAXRSServerFactoryBean rs = new JAXRSServerFactoryBean() ;
		BookRestfulService ss = new BookRestfulService() ;
		rs.setServiceBean(ss) ;
		rs.setAddress("http://127.0.0.1/") ;
		rs.create() ;
		
	}
}
