package com.revature.app;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.classes.DemoClass;
import com.revature.classes.JsonClass;
import com.revature.classes.XmlClass;

public class App {
	public static void main(String[] args) {
		//xmlMethod();
		//jsonMethod();
		DemoClass testClass = new DemoClass();
		testClass.setI(1);
		testClass.setS("hello world");
		testClass.setB(true);
		//System.out.println(marshalToXml(testClass));
		//System.out.println(marshalToJson(testClass));
		BufferedReader jsonData = new BufferedReader(new StringReader("{\"i\":56,\"s\":\"Hi there!\",\"b\":false}"));
		System.out.println(marshalToJson(jsonData));
	}
	
	public static String marshalToXml(DemoClass democlass) {
		try {
			JAXBContext context = JAXBContext.newInstance(DemoClass.class);
			
			// Marshalling
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			StringWriter sw = new StringWriter();
			marshaller.marshal(democlass, sw);
			
			return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static DemoClass unmarshalFromXml(String xml) { return null;}
	
	public static String marshalToJson(BufferedReader democlass) { 	
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			String s = mapper.writeValueAsString(democlass);
			System.out.println(s);
			
			BufferedReader empty = mapper.readValue(s, BufferedReader.class);
			return empty.getI() + " " + empty.getS() +  " " + empty.isB();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static DemoClass unmarshalFromJson(String xml) { return null;}
	
	public static void jsonMethod() {
		JsonClass json = new JsonClass(), empty;
		json.setI(56);
		json.setB(false);
		json.setS("Hi there!");
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			String s = mapper.writeValueAsString(json);
			System.out.println(s);
			
			empty = mapper.readValue(s, JsonClass.class);
			System.out.println(empty.getI() + " " + empty.getS());
		} catch (Exception e) {
			
		}
	}
	
	public static void xmlMethod() {
		XmlClass xml = new XmlClass(), empty;
		xml.setI(10);
		xml.setS("Hello");
		xml.setB(true);
		
		try {
			JAXBContext context = JAXBContext.newInstance(XmlClass.class);
			
			// Marshalling
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			marshaller.marshal(xml, System.out);
			
			// Unmarshalling
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			empty = (XmlClass) unmarshaller.unmarshal(new StringReader(
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
					+ "<XmlClass>\r\n"
					+ "    <b>true</b>\r\n"
					+ "    <i>10</i>\r\n"
					+ "    <s>Hello</s>\r\n"
					+ "</XmlClass>"));
			
			System.out.println(empty.getI() + " " + empty.getS());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}