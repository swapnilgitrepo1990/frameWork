package com.nuance.eq.temp;



	import java.io.*;  
	
	class randd{  
		
		enum PLtypes {
		    highlevel, machinelevel, assembly, middlelevel
		    
		    

			} 
		
		
	   public static void main(String args[]) throws IOException{//declare exception  
		   PLtypes lang;
	        System.out.println(" Here are lists of constants");
	        PLtypes alltypes[] = PLtypes.values();
	        for (PLtypes a: alltypes)
	            System.out.println(a);
	        System.out.println();
	        lang = PLtypes.valueOf("assembly");
	        System.out.println("Value is:" + lang);
	        StringBuffer sb1=new StringBuffer("Hello");  
	        sb1.replace(1,3,"Java");  
	        System.out.println(sb1);//prints HJavalo  
	        
	        
	        long startTime = System.currentTimeMillis();  
	        StringBuffer sb = new StringBuffer();  
	        for (int i=0; i<10000; i++){  
	            sb.append("Tpoint");  
	        }  
	        System.out.println("Time taken by StringBuffer: " + (System.currentTimeMillis() - startTime) + "ms");  
	        startTime = System.currentTimeMillis();  
	        StringBuilder sb2 = new StringBuilder();  
	        for (int i=0; i<10000; i++){  
	            sb2.append("Tpoint");  
	        }  
	        System.out.println("Time taken by StringBuilder: " + (System.currentTimeMillis() - startTime) + "ms");
	        
	        String str = new String("abc");
	        String str1 = new String("abc");
	        
	        System.out.println(str1.equals(str));
	        System.out.println(str1.hashCode());
	        System.out.println(str.hashCode());
	        new randd().foo("gg");
	        
	  }  
	   public void foo(String s) {
			 System.out.println("String");
			 }

			 public void foo(StringBuffer sb){
			 System.out.println("StringBuffer");
			 }
	} 