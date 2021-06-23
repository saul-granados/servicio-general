package com.cominvi.app.oauth.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

public class Utils {
	
	
	public static final String KEY_ACTIVACION = "C0M1nV1q2aw3se4lo9KT8";
	public static final String TOKEN_SMS = "632ea5f4805244e6b3ee75453eb4e010";

	
	public String readFileToString(String fileName) {
		String text = "";
		
		Resource resource = new ClassPathResource(fileName);
		try {
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			text = new String(bdata, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return text;
	}
	
	public static Date StringToDate(String strfecha) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fecha = null;
		try {
			fecha = df.parse(strfecha);
		} catch (ParseException e) {
			return null;
		}
		
		return fecha;
	}
	
	public static String DateToString(Date fecha) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(fecha);
	}
	
	public static String regresaCuerpoEmailOrdenCompra(String usuario, String password, String nombre) {
		return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
				"<head>\r\n" + 
				"<meta http-equiv=”Content-Type” content=”text/html; charset=ISO-8859-1″ /> \r\n" + 
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\r\n" + 
				"<meta charset=\"UTF-8\" \r\n" + 
				"</head>\r\n" + 
				"<body style=\"margin: 0; padding: 0;\">\r\n" + 
				"	<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">	\r\n" + 
				"		<tr>\r\n" + 
				"			<td style=\"padding: 10px 0 30px 0;\">\r\n" + 
				"				<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">\r\n" + 
				"					<tr>\r\n" + 
				"						<td align=\"center\" bgcolor=\"#C0C0C0\" style=\"padding: 10px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">\r\n" + 
				"							Alta de usuario\r\n" + 
				"						</td>\r\n" + 
				"					</tr>\r\n" + 
				"					<tr>\r\n" + 
				"						<td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\r\n" + 
				"							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"								<tr>\r\n" + 
				"									<td style=\"padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\r\n" + 
				"                    ¡Bienvenid@! "+nombre+" <br>\r\n" + 
				"                    El usuario se registro con &eacute;xito, para el ingreso al portal. <br>\r\n" + 
				"                    Usuario: <strong>"+usuario+"</strong><br>\r\n" +
				"                    Contraseña: <strong>"+password+"</strong>\r\n" + 
				"									</td>\r\n" + 
				"								</tr>\r\n" + 
				"                <tr>\r\n" + 
				"                  <td style=\"padding: 20px 0 30px 0; color: #153643; font-family:     Arial, sans-serif; font-size: 16px; line-height: 20px; text-align: center\">\r\n" + 
				"                    <a href=\"http://erp.cominvi.com.mx\">erp.cominvi.com.mx</a>\r\n" + 
				"                  </td>\r\n" + 
				"                </tr>\r\n" + 
				"							</table>\r\n" + 
				"						</td>\r\n" + 
				"					</tr>\r\n" + 
				"					<tr>\r\n" + 
				"						<td bgcolor=\"#E48134\" style=\"padding: 30px 30px 30px 30px;\">\r\n" + 
				"							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"								<tr>\r\n" + 
				"									<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"75%\">\r\n" + 
				"										&reg; CoMinVi SA de CV<br/>\r\n" + 
				"										<a href=\"https://www.cominvi.com.mx/\" style=\"color: #ffffff;\"><font color=\"#ffffff\">www.cominvi.com.mx</font></a> \r\n" + 
				"										<br/>Blvd. Paseo de los Insurgentes 3356 Libramiento. <br/>\r\n" + 
				"									Corporativo Puerta Baj&iacute;o piso 7. <br/>\r\n" + 
				"									Colonia Cumbres del Campestre <br/>\r\n" + 
				"									C.P. 37128 <br/>\r\n" + 
				"									Le&oacute;n, Gto. <br/>\r\n" + 
				"									M&eacute;xico. <br/>\r\n" + 
				"									Tel&eacute;fono: (+52) 477 104 3566 y (+52) 473 733 3927 \r\n" + 
				"									</td>\r\n" + 
				"									<td align=\"right\" width=\"25%\">\r\n" + 
				"										<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
				"											<tr>\r\n" + 
				"												<td style=\"font-family: Arial, sans-serif; font-size: 9px;\">\r\n" + 
				"													<a href=\"https://www.cominvi.com.mx/\" style=\"color: #ffffff;\">\r\n" + 
				"														<img src=\"https://cominvi.com.mx/assets/img/logo.png\" alt=\"CoMinVi\" width=\"158\" height=\"38\" style=\"display: block;\" border=\"0\" />\r\n" + 
				"														\r\n" + 
				"													</a>\r\n" + 
				"												</td>\r\n" + 
				"												<td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\r\n" + 
				"\r\n" + 
				"											</tr>\r\n" + 
				"										</table>\r\n" + 
				"									</td>\r\n" + 
				"								</tr>\r\n" + 
				"							</table>\r\n" + 
				"						</td>\r\n" + 
				"					</tr>\r\n" + 
				"				</table>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"</body>\r\n" + 
				"</html>";
	}
        
    public static String regresaCuerpoEmailUpdateUser(String usuario, String cambios, String nombre) {
		return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
				"<head>\r\n" + 
				"<meta http-equiv=”Content-Type” content=”text/html; charset=ISO-8859-1″ /> \r\n" + 
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\r\n" + 
				"<meta charset=\"UTF-8\" \r\n" + 
				"</head>\r\n" + 
				"<body style=\"margin: 0; padding: 0;\">\r\n" + 
				"	<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">	\r\n" + 
				"		<tr>\r\n" + 
				"			<td style=\"padding: 10px 0 30px 0;\">\r\n" + 
				"				<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">\r\n" + 
				"					<tr>\r\n" + 
				"						<td align=\"center\" bgcolor=\"#C0C0C0\" style=\"padding: 10px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">\r\n" + 
				"							EDICION DE USUARIO\r\n" + 
				"						</td>\r\n" + 
				"					</tr>\r\n" + 
				"					<tr>\r\n" + 
				"						<td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\r\n" + 
				"							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"								<tr>\r\n" + 
				"									<td style=\"padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\r\n" + 
				"                    ¡Bienvenid@! "+nombre+" <br>\r\n" + 
				"                    El usuario se modifico con &eacute;xito para el ingreso al portal. <br>\r\n" +
				"                    Usuario: <strong>"+usuario+"</strong><br>\r\n" +
				"                    "+cambios+"\r\n" + 
				"									</td>\r\n" + 
				"								</tr>\r\n" + 
				"                <tr>\r\n" + 
				"                  <td style=\"padding: 20px 0 30px 0; color: #153643; font-family:     Arial, sans-serif; font-size: 16px; line-height: 20px; text-align: center\">\r\n" + 
				"                    <a href=\"http://erp.cominvi.com.mx\">erp.cominvi.com.mx</a>\r\n" + 
				"                  </td>\r\n" + 
				"                </tr>\r\n" + 
				"							</table>\r\n" + 
				"						</td>\r\n" + 
				"					</tr>\r\n" + 
				"					<tr>\r\n" + 
				"						<td bgcolor=\"#E48134\" style=\"padding: 30px 30px 30px 30px;\">\r\n" + 
				"							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"								<tr>\r\n" + 
				"									<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"75%\">\r\n" + 
				"										&reg; CoMinVi SA de CV<br/>\r\n" + 
				"										<a href=\"https://www.cominvi.com.mx/\" style=\"color: #ffffff;\"><font color=\"#ffffff\">www.cominvi.com.mx</font></a> \r\n" + 
				"										<br/>Blvd. Paseo de los Insurgentes 3356 Libramiento. <br/>\r\n" + 
				"									Corporativo Puerta Baj&iacute;o piso 7. <br/>\r\n" + 
				"									Colonia Cumbres del Campestre <br/>\r\n" + 
				"									C.P. 37128 <br/>\r\n" + 
				"									Le&oacute;n, Gto. <br/>\r\n" + 
				"									M&eacute;xico. <br/>\r\n" + 
				"									Tel&eacute;fono: (+52) 477 104 3566 y (+52) 473 733 3927 \r\n" + 
				"									</td>\r\n" + 
				"									<td align=\"right\" width=\"25%\">\r\n" + 
				"										<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
				"											<tr>\r\n" + 
				"												<td style=\"font-family: Arial, sans-serif; font-size: 9px;\">\r\n" + 
				"													<a href=\"https://www.cominvi.com.mx/\" style=\"color: #ffffff;\">\r\n" + 
				"														\r\n" + 
				"														\r\n" + 
				"													</a>\r\n" + 
				"												</td>\r\n" + 
				"												<td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\r\n" + 
				"\r\n" + 
				"											</tr>\r\n" + 
				"										</table>\r\n" + 
				"									</td>\r\n" + 
				"								</tr>\r\n" + 
				"							</table>\r\n" + 
				"						</td>\r\n" + 
				"					</tr>\r\n" + 
				"				</table>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"</body>\r\n" + 
				"</html>";
	}  
	
    public static String regresaCuerpoEmailNuevoUsuario(String nombre, String correo, String url) {
		
    	return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
    			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n" + 
    			" <head> \r\n" + 
    			"  <meta charset=\"UTF-8\"> \r\n" + 
    			"  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \r\n" + 
    			"  <meta name=\"x-apple-disable-message-reformatting\"> \r\n" + 
    			"  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \r\n" + 
    			"  <meta content=\"telephone=no\" name=\"format-detection\"> \r\n" + 
    			"  <title>Nueva plantilla de correo electrónico 2020-08-24</title> \r\n" + 
    			"  <style type=\"text/css\">\r\n" + 
    			"@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important; line-height:150%!important } h1 { font-size:30px!important; text-align:left; line-height:120% } h2 { font-size:26px!important; text-align:left; line-height:120% } h3 { font-size:20px!important; text-align:left; line-height:120% } h1 a { font-size:30px!important; text-align:left } h2 a { font-size:26px!important; text-align:left } h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } td .es-infoblock p, td .es-infoblock ul li, td .es-infoblock ol li, td .es-infoblock a { font-size:12px } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button { font-size:20px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }\r\n" + 
    			"#outlook a {\r\n" + 
    			"	padding:0;\r\n" + 
    			"}\r\n" + 
    			".ExternalClass {\r\n" + 
    			"	width:100%;\r\n" + 
    			"}\r\n" + 
    			".ExternalClass,\r\n" + 
    			".ExternalClass p,\r\n" + 
    			".ExternalClass span,\r\n" + 
    			".ExternalClass font,\r\n" + 
    			".ExternalClass td,\r\n" + 
    			".ExternalClass div {\r\n" + 
    			"	line-height:100%;\r\n" + 
    			"}\r\n" + 
    			".es-button {\r\n" + 
    			"	mso-style-priority:100!important;\r\n" + 
    			"	text-decoration:none!important;\r\n" + 
    			"}\r\n" + 
    			"a[x-apple-data-detectors] {\r\n" + 
    			"	color:inherit!important;\r\n" + 
    			"	text-decoration:none!important;\r\n" + 
    			"	font-size:inherit!important;\r\n" + 
    			"	font-family:inherit!important;\r\n" + 
    			"	font-weight:inherit!important;\r\n" + 
    			"	line-height:inherit!important;\r\n" + 
    			"}\r\n" + 
    			".es-desk-hidden {\r\n" + 
    			"	display:none;\r\n" + 
    			"	float:left;\r\n" + 
    			"	overflow:hidden;\r\n" + 
    			"	width:0;\r\n" + 
    			"	max-height:0;\r\n" + 
    			"	line-height:0;\r\n" + 
    			"	mso-hide:all;\r\n" + 
    			"}\r\n" + 
    			"</style> \r\n" + 
    			" </head> \r\n" + 
    			" <body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"> \r\n" + 
    			"  <div class=\"es-wrapper-color\" style=\"background-color:#F0F4F2\"> \r\n" + 
    			"\r\n" + 
    			"   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"> \r\n" + 
    			"     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"      <td valign=\"top\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \r\n" + 
    			"         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"          <td align=\"center\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"           <table class=\"es-header-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" bgcolor=\"#4b5657\" style=\"padding:0;Margin:0;background-color:#4B5657\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"left\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#737373\"><br><br></p></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"           </table></td> \r\n" + 
    			"         </tr> \r\n" + 
    			"       </table> \r\n" + 
    			"       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \r\n" + 
    			"         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"          <td align=\"center\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;padding-bottom:40px\"> \r\n" + 
    			"               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\"> \r\n" + 
    			"                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td class=\"es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0;padding-bottom:20px\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#4B5657;text-align:center\">Bienvenido "+nombre+" </h1></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0\"><h4 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;color:#4B5657\">Se ha creado su cuenta con la dirección de correo electrónico <u> "+correo+" </u></h4><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:18px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:27px;color:#333333\">Para activar tu cuenta es necesario verificar tu correo electrónico, a continuación debes de presionar el boton de activar</p><br><br></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:20px\"> \r\n" + 
    			"                       \r\n" + 
    			"                       <span class=\"msohide es-button-border\" style=\"border-style:solid;border-color:#5A6767;background:#4B5657;border-width:3px;display:inline-block;border-radius:0px;width:auto;mso-hide:all\">\r\n" + 
    			"                        <a href=\""+url+"\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;color:#FFFFFF;border-style:solid;border-color:#4B5657;border-width:15px 45px 15px 50px;display:inline-block;background:#4B5657;border-radius:0px;font-weight:bold;font-style:normal;line-height:17px;width:auto;text-align:center\">Activar</a></span> \r\n" + 
    			"                       </td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" bgcolor=\"#fafafa\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;padding-bottom:40px;background-color:#FAFAFA\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333\">Si el enlace no funciona, copia esta dirección en tu buscador:</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333\"><a href=\""+url+"\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:14px;text-decoration:none;color:#C44D39\">"+url+"</a></p></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333\">Bienvenido!!!</p></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"           </table></td> \r\n" + 
    			"         </tr> \r\n" + 
    			"       </table> \r\n" + 
    			"       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \r\n" + 
    			"         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"          <td align=\"center\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#4B5657\">© Cominvi. Todos los derechos reservados.</p></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"           </table></td> \r\n" + 
    			"         </tr> \r\n" + 
    			"       </table></td> \r\n" + 
    			"     </tr> \r\n" + 
    			"   </table> \r\n" + 
    			"  </div>  \r\n" + 
    			" </body>\r\n" + 
    			"</html>";
    	
	}
    
    public static String regresaCuerpoEmailRecuperaPassword(String correo, String url) {
    	return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
    			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n" + 
    			" <head> \r\n" + 
    			"  <meta charset=\"UTF-8\"> \r\n" + 
    			"  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \r\n" + 
    			"  <meta name=\"x-apple-disable-message-reformatting\"> \r\n" + 
    			"  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \r\n" + 
    			"  <meta content=\"telephone=no\" name=\"format-detection\"> \r\n" + 
    			"  <title>RecuperaContraseña 2020-08-24</title> \r\n" + 
    			"  <style type=\"text/css\">\r\n" + 
    			"@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important; line-height:150%!important } h1 { font-size:30px!important; text-align:left; line-height:120% } h2 { font-size:26px!important; text-align:left; line-height:120% } h3 { font-size:20px!important; text-align:left; line-height:120% } h1 a { font-size:30px!important; text-align:left } h2 a { font-size:26px!important; text-align:left } h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } td .es-infoblock p, td .es-infoblock ul li, td .es-infoblock ol li, td .es-infoblock a { font-size:12px } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button { font-size:20px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }\r\n" + 
    			"#outlook a {\r\n" + 
    			"	padding:0;\r\n" + 
    			"}\r\n" + 
    			".ExternalClass {\r\n" + 
    			"	width:100%;\r\n" + 
    			"}\r\n" + 
    			".ExternalClass,\r\n" + 
    			".ExternalClass p,\r\n" + 
    			".ExternalClass span,\r\n" + 
    			".ExternalClass font,\r\n" + 
    			".ExternalClass td,\r\n" + 
    			".ExternalClass div {\r\n" + 
    			"	line-height:100%;\r\n" + 
    			"}\r\n" + 
    			".es-button {\r\n" + 
    			"	mso-style-priority:100!important;\r\n" + 
    			"	text-decoration:none!important;\r\n" + 
    			"}\r\n" + 
    			"a[x-apple-data-detectors] {\r\n" + 
    			"	color:inherit!important;\r\n" + 
    			"	text-decoration:none!important;\r\n" + 
    			"	font-size:inherit!important;\r\n" + 
    			"	font-family:inherit!important;\r\n" + 
    			"	font-weight:inherit!important;\r\n" + 
    			"	line-height:inherit!important;\r\n" + 
    			"}\r\n" + 
    			".es-desk-hidden {\r\n" + 
    			"	display:none;\r\n" + 
    			"	float:left;\r\n" + 
    			"	overflow:hidden;\r\n" + 
    			"	width:0;\r\n" + 
    			"	max-height:0;\r\n" + 
    			"	line-height:0;\r\n" + 
    			"	mso-hide:all;\r\n" + 
    			"}\r\n" + 
    			"</style> \r\n" + 
    			" </head> \r\n" + 
    			" <body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"> \r\n" + 
    			"  <div class=\"es-wrapper-color\" style=\"background-color:#F0F4F2\"> \r\n" + 
    			"   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"> \r\n" + 
    			"     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"      <td valign=\"top\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \r\n" + 
    			"         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"          <td align=\"center\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"           <table class=\"es-header-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" bgcolor=\"#4b5657\" style=\"padding:0;Margin:0;background-color:#4B5657\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"left\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#737373\"><br><br></p></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"           </table></td> \r\n" + 
    			"         </tr> \r\n" + 
    			"       </table> \r\n" + 
    			"       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \r\n" + 
    			"         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"          <td align=\"center\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;padding-bottom:40px\"> \r\n" + 
    			"               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\"> \r\n" + 
    			"                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0\"><h4 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;color:#4B5657\">Se mando una solicitud para reestablecer la contraseña del&nbsp;correo&nbsp;<u>"+correo+"</u></h4><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:18px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:27px;color:#333333\">Para reestablecer&nbsp;la contraseña de su&nbsp;cuenta haga clic en el boton, en caso de que usted no haya realizo la solicitud, ignore este correo electrónico.</p><span style=\"color:#FF0000;font-size:13px\">El enlace es valido por los proximos 15 minutos.</span><br><br></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:20px\"> \r\n" + 
    			"                       <!--[if !mso]><!-- --><span class=\"msohide es-button-border\" style=\"border-style:solid;border-color:#5A6767;background:#4B5657;border-width:3px;display:inline-block;border-radius:0px;width:auto;mso-hide:all\"><a href=\""+url+"\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:13px;color:#FFFFFF;border-style:solid;border-color:#4B5657;border-width:15px 45px 15px 50px;display:inline-block;background:#4B5657;border-radius:0px;font-weight:bold;font-style:normal;line-height:16px;width:auto;text-align:center\">Reestablecer Contraseña</a></span> \r\n" + 
    			"                       <!--<![endif]--></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" bgcolor=\"#fafafa\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;padding-bottom:40px;background-color:#FAFAFA\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333\">Si el enlace no funciona, copia esta dirección en tu buscador:</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333\"><a href=\""+url+"\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:14px;text-decoration:none;color:#C44D39\">"+url+"</a></p></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"           </table></td> \r\n" + 
    			"         </tr> \r\n" + 
    			"       </table> \r\n" + 
    			"       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \r\n" + 
    			"         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"          <td align=\"center\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"padding:0;Margin:0\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"> \r\n" + 
    			"                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                         <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px\"></td> \r\n" + 
    			"                         </tr> \r\n" + 
    			"                       </table></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"             <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\"> \r\n" + 
    			"               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                 <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\"> \r\n" + 
    			"                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \r\n" + 
    			"                     <tr style=\"border-collapse:collapse\"> \r\n" + 
    			"                      <td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#4B5657\">©Cominvi. Todos los derechos reservados.</p></td> \r\n" + 
    			"                     </tr> \r\n" + 
    			"                   </table></td> \r\n" + 
    			"                 </tr> \r\n" + 
    			"               </table></td> \r\n" + 
    			"             </tr> \r\n" + 
    			"           </table></td> \r\n" + 
    			"         </tr> \r\n" + 
    			"       </table></td> \r\n" + 
    			"     </tr> \r\n" + 
    			"   </table> \r\n" + 
    			"  </div>  \r\n" + 
    			" </body>\r\n" + 
    			"</html>";
    }
    
    
}//-->
