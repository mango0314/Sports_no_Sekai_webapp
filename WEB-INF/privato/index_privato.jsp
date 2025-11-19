<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= "it.unirc.txw.progetto.beans.scontro.*"%>
    <%@ page import= "it.unirc.txw.progetto.beans.torneo.*"%>
    <%@ page import= "it.unirc.txw.progetto.beans.squadra.*"%>
    <%@ page import= "it.unirc.txw.progetto.beans.giocatore.*"%>
    <%@page import="java.text.SimpleDateFormat"%>
    
    
        
    
<%@ page import="java.util.Vector"%>



<!DOCTYPE html>
<html lang="en">

<head>

<% Squadra squadra = (Squadra) session.getAttribute("squadra");

	int ruolo = ( int ) session.getAttribute("ruolo");
%>

	<% if( ruolo == 0){%>
	  <title> Admin - Sports no Sekai</title>
	  <% } else{ %>
	
	
  <title> <%= squadra.getNome() %> - Sports no Sekai</title>
  <% } %>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Favicon-->
<link rel="apple-touch-icon" sizes="180x180"
	href="../assets/ico/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="../assets/ico/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../assets/ico/favicon-16x16.png">
<link rel="manifest" href="../assets/ico/site.webmanifest">

  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="../fonts/icomoon/style.css">

  <link rel="stylesheet" href="../css/bootstrap/bootstrap.css">
  <link rel="stylesheet" href="../css/jquery-ui.css">
  <link rel="stylesheet" href="../css/owl.carousel.min.css">
  <link rel="stylesheet" href="../css/owl.theme.default.min.css">
  <link rel="stylesheet" href="../css/owl.theme.default.min.css">

  <link rel="stylesheet" href="../css/jquery.fancybox.min.css">

  <link rel="stylesheet" href="../css/bootstrap-datepicker.css">

  <link rel="stylesheet" href="../fonts/flaticon/font/flaticon.css">

  <link rel="stylesheet" href="../css/aos.css">

  <link rel="stylesheet" href="../css/style.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
  



</head>

<body>

<!-- Navigation-->
	<% if ( ruolo == 0){ %>
	        <%@ include file="admin/frame_navigation_admin.html" %>
	        <% } else{ %>
	
        <%@ include file="utente/frame_navigation_utente.html" %>
        
        
        <%  }
        Giocatore giocatore = (Giocatore) request.getAttribute("giocatore");
        Vector<Giocatore> giocatori = (Vector<Giocatore>) request.getAttribute("giocatori");
        
	Vector<Torneo> lista_tuttitornei = (Vector<Torneo>) request.getAttribute("lista_tuttitornei");


      	String LogoSquadra = "LogoSquadra?id=" + squadra;
      

      
      %>
        
	<% if ( ruolo == 0){ %>
	
	<div class="hero overlay" style="background-image: url('../images/admin sfondo.png');">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-5 mx-auto text-center">
            <h1 class="text-white">Admin</h1>
            <p>Cosa cambiamo oggi?</p>
           
          </div>
        </div>
      </div>
    </div>

<% } else{ %>
    <div class="hero text-center py-5 bg-light mt-custom">
  <div class="container">
    <!-- Logo grande al centro -->
    <img src="../images/<%= squadra.getLogo() %>" 
         alt="Logo <%= squadra.getNome() %>" 
         class="img-fluid mb-4" 
         style="max-height: 400px;">

    <!-- Nome torneo -->
    <h1 class="display-4"> <%= squadra.getNome() %> </h1>
    
  </div>
</div>
<% } %>
    

  

    <% if( ruolo == 0 ){
    	
    	
    	%>
	
	 <div class="site-section bg-dark">
  	<div class="container">
 


        <div class="row">
          <div class="col-12 title-section">
            <h2 class="heading d-inline-flex align-items-center">
					  Tornei disponibili
					  <a 
					    href="RichiediAggiungiTorneo" 
					    class="btn btn-primary btn-sm ml-3" 
					    title="Aggiungi Torneo"
					  >
					    <i class="bi bi-plus"></i>
					  </a>
					</h2>


          </div>
          
          <%
          	if(lista_tuttitornei != null && !lista_tuttitornei.isEmpty()){
          		for (Torneo t : lista_tuttitornei){
          			String dettaglioURL = "DettaglioTorneoAdmin?id=" + t.getId() + "&sport_id=" + t.getSport_id();
          			String modificaURL = "RichiediModificaTorneo?id=" + t.getId() + "&sport_id=" + t.getSport_id();
          			String eliminaURL = "EliminaTorneo?id=" + t.getId() + "&sport_id=" + t.getSport_id();
          			
          			String logoURL = "LogoTornei?=" + t.getId();
          		
          		
          	
          
          %>
          <div class="col-lg-6 mb-4">
            <div class="bg-light p-2 rounded">
              <div class="widget-body">
                  <div class="widget-vs">
                    <div class="d-flex align-items-center justify-content-around justify-content-between w-100">
                      <div class="team-1 text-center">
                      <a href="<%= dettaglioURL %>">
                      <img src="../images/<%= t.getLogo() %>" 
                       alt="Logo <%= t.getNome() %>" 
                       class="img-fluid mb-2"/>
                        <h3><%= t.getNome() %></h3>
                        
                        </a>
                        
                        
                        <a href="<%=modificaURL%>"><button class="btn btn-primary">MODIFICA</button></a>
						<a href="<%=eliminaURL%>"><button class="btn btn-primary">ELIMINA</button></a>
                        
                      </div>
                                            
                    </div>
                  </div>
                </div>


              
            </div>
          </div>
          
          <% }
          	} else { 
          
                    
          %>
   			<div class="col-12">
      			<div class="alert alert-info text-center">
        		Al momento non ci sono tornei di calcio disponibili.
      		</div>
    		</div>
  			<%
   				 }
 			 %>
 			 
 			 </div>
 			 </div>
    
    
    
    
    </div> <!-- .site-section -->
    
	
  

  
  
  
  <% } else{ %>
  
  <div class="site-section bg-dark">
  <div class="container">
  
    <div class="row">
      <div class="col-12 title-section">
        <h2 class="heading">Giocatori</h2>
      </div>
      
      
      

				       <table class="table custom-table">
				  <thead>
				    <tr>
				      <th scope="col"><h4> Nome </h4></th>
				      <th scope="col"> <h4>Cognome </h4>  </th>
				      <th scope="col"><h4>Data di Nascita  </h4>  </th>
				      <th scope="col"> <h4>Numero di Maglia</h4></th>
				      
				    </tr>
				  </thead>
				  
				  <%
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // creo l'oggetto

      	if(giocatori != null || !giocatori.isEmpty()){
      		for ( Giocatore g : giocatori){
      			String dataStr = sdf.format(g.getDataDiNascita()); 

      			
      		
      	
      
      
      %>
				  
				  <tbody>
				  
				  
				    <tr>
				      
				      <td><%= g.getNome() %></td>
				      <td><%= g.getCognome() %></td>
				      <td><%= dataStr %></td>
				      <td> <%= g.getNumero_di_maglia() %></td>
				    </tr>
				    
				  </tbody>
				
  		

      	
      	<%  }
      	}
      		else {
      	%>
      
      		
      	
  			
  				<div class="col-12">
      			<div class="alert alert-info text-center">
        		Al momento non ci sono giocatori nella squadra selezionata.
      		</div>
    		</div>
  			<%
   				 }
 			 %>
 			 
 			</table>
 			 
  
</div>
</div>
</div>
<% } %>
    
    

   

       <!-- Foot-->
        <%@ include file="foot.html" %>
  



  <!-- .site-wrap -->
  

  

  <script src="js/jquery-3.3.1.min.js"></script>
  <script src="js/jquery-migrate-3.0.1.min.js"></script>
  <script src="js/jquery-ui.js"></script>
  <script src="js/popper.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/owl.carousel.min.js"></script>
  <script src="js/jquery.stellar.min.js"></script>
  <script src="js/jquery.countdown.min.js"></script>
  <script src="js/bootstrap-datepicker.min.js"></script>
  <script src="js/jquery.easing.1.3.js"></script>
  <script src="js/aos.js"></script>
  <script src="js/jquery.fancybox.min.js"></script>
  <script src="js/jquery.sticky.js"></script>
  <script src="js/jquery.mb.YTPlayer.min.js"></script>


  <script src="js/main.js"></script>

</body>

</html>