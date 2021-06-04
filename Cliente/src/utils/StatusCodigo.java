/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
public enum StatusCodigo {
   OK {
       @Override
       public String enumToString(){
           return "Sucesso";
       }
   },
   INTERNAL_SERVER_ERRROR{
       @Override
       public String enumToString(){
           return "Falha no servidor";
       }
   },
   BAD_REQUEST{
       @Override
       public String enumToString(){
           return "BAD_REQUEST";
       }
   },
   UNAUTHORIZED{
       @Override
       public String enumToString(){
           return "UNAUTHORIZED";
       }
   },
   UNPROCESSABLE_ENTITY{
       @Override
       public String enumToString(){
           return "UNPROCESSABLE_ENTITY";
       }
   },
   NOT_FOUND{
      @Override
       public String enumToString(){
           return "NOT_FOUND";
       } 
   },
   CONFLICT{
       @Override
       public String enumToString(){
           return "CONFLICT";
       } 
   },
   FORBIDDEN{
       @Override
       public String enumToString(){
           return "FORBIDDEN";
       } 
   },
   NOT_ACCEPTABLE{
       public String enumToString(){
           return "NOT_ACCEPTABLE";
       }
   },
   UNSUPPORTED_MEDIA_TYPE{
       public String enumToString(){
           return "UNSUPPORTED_MEDIA_TYPE";
       }
   };
   public abstract String enumToString();
}

