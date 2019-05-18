package com.practica3.myapp;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.practica3.myapp.DAOInterfazUsuarios;
import com.practica3.myapp.Usuario;

import es.gob.jmulticard.jse.provider.DnieProvider;
import es.gob.jmulticard.jse.smartcardio.SmartcardIoConnection;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	 
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private DAOInterfazUsuarios dao;
	

	@RequestMapping(value = "/autenticar", method = {RequestMethod.POST,RequestMethod.GET})
	public String validar(HttpServletRequest req,Model model) throws InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, SignatureException, IOException{
		
		
		//Recogemos parametros de la petición 
				String Usu=req.getParameter("Usu");
				String nif=req.getParameter("nif");
				String fecha=req.getParameter("fecha");
				String ClavePublica=req.getParameter("key");
				String firma=req.getParameter("firma");
				String respuesta1 = null;
			
				byte[] signatureBytes =DatatypeConverter.parseBase64Binary(firma); //Decodificamos base64 tanto de la firma como de la clave publica
				byte [] claveDecodificada = DatatypeConverter.parseBase64Binary(ClavePublica);
				
				
				if(dao.ComprobarUsuario(Usu,nif)!=null){ //Comprobamos que los parametros del nombre de usuario y DNI existen en la base de datos
					 
					
					String clavesecreta=dao.obtenerClaveServicio(nif); //Si coincide Usuario y DNI, se recoge la clave de servicio registrada en la base de datos
					
					
					String DatosSinFirmar=Usu+nif+fecha+clavesecreta; //Con los datos registrados montamos variable para comparar con la firma recibida
					
					
					//Formato de clave pública para la clave recogida del cliente
					X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(claveDecodificada);
					//Para una clave pública RSA
					KeyFactory kf = KeyFactory.getInstance("RSA");
					PublicKey publickey;
					
					try {
						
						publickey = kf.generatePublic(x509EncodedKeySpec);
						
						//Firma con seguridad SHA1
						Signature signature1 = Signature.getInstance("SHA1withRSA");
						//Se usa el formato de clave pública
						signature1.initVerify(publickey);
						
					//Se introducen los datos
						signature1.update(DatosSinFirmar.getBytes());
						
						
						//Se verifica con los datos firmados
						Boolean verificado = signature1.verify(signatureBytes);
						
						
						
						//Si se verifica correctamente
						if(verificado){ //Si se verifica, llamamos al metodo para insertar la fecha en la que se hizo la verificacion
							dao.InsertarFecha(nif,fecha);
							System.out.println("El usuario "+Usu+ " con DNI: "+nif+" se ha verificado correctamente");
							respuesta1 = "Verificado";
						}else{
							respuesta1 = "No Verificado";
							System.out.println("No se pude verificar el usuario "+Usu);
						}
						
					} catch (InvalidKeySpecException e) {
						respuesta1 = "Error al intentar verificar";
					}
					
					
				         
			}else respuesta1="Los datos del DNI introducidos en el lector no estan en la base de datos";
			    
			
				
			req.setAttribute("resp", respuesta1);
			
				return "respuesta";  
		     
	}
	
	
}
