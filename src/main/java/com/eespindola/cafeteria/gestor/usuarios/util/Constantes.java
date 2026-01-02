package com.eespindola.cafeteria.gestor.usuarios.util;

public class Constantes {

  public static final String SP_GET_ALL = "SP_UsuarioGetAll";
  public static final String SP_GET_BY_FOLIO = "SP_UsuarioGetByFolio";
  public static final String SP_INSERT = "SP_UsuarioInsert";
  public static final String SP_UPDATE = "SP_UsuarioUpdate";
  public static final String SP_DELETE = "SP_UsuarioDelete";

  public static final String ENDPOINT_SEND_MESSAGE = "/telegram/bot/send-message";

  private Constantes(){
    throw new IllegalArgumentException("Util class");
  }
}
