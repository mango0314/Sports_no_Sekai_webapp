<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "it.unirc.txw.progetto.beans.torneo.*"%>
<%@ page import="java.util.Vector"%>

    
<!DOCTYPE html>
<html lang="en">

<head>
	
	  <%
    
    // Recupera la lista dalla request
    Vector<Torneo> tornei = (Vector<Torneo>) request.getAttribute("tornei");
      int sportId = (Integer) request.getAttribute("sportId");
			%>
	<% if( sportId == 1){ %>
  <title>Calcio - Sports no Sekai</title>
  	<%  } else if( sportId == 2){ %>
  	  <title>Basketball - Sports no Sekai</title>
  	  <%  } else { %>
  	    <title>Pallavolo - Sports no Sekai</title>
  	  <% } %>
  	  
  	
  	
  	
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Favicon-->
<link rel="apple-touch-icon" sizes="180x180"
	href="assets/ico/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="assets/ico/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="assets/ico/favicon-16x16.png">
<link rel="manifest" href="assets/ico/site.webmanifest">

  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="fonts/icomoon/style.css">

  <link rel="stylesheet" href="css/bootstrap/bootstrap.css">
  <link rel="stylesheet" href="css/jquery-ui.css">
  <link rel="stylesheet" href="css/owl.carousel.min.css">
  <link rel="stylesheet" href="css/owl.theme.default.min.css">
  <link rel="stylesheet" href="css/owl.theme.default.min.css">

  <link rel="stylesheet" href="css/jquery.fancybox.min.css">

  <link rel="stylesheet" href="css/bootstrap-datepicker.css">

  <link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">

  <link rel="stylesheet" href="css/aos.css">

  <link rel="stylesheet" href="css/style.css">



</head>

<body>

<!-- Navigation-->
        <%@ include file="frame_navigation.html" %>
          
        

	<% if(sportId == 1 ){ %>
    <div class="hero overlay" style="background-image: url('images/sfondo calcio.png');">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-5 mx-auto text-center">
            <h1 class="text-white">Calcio</h1>
            <p>Qui sotto troverete i tornei attivi, dove per parteciparvi è necessario creare un account e registrare la tua squadra</p>
           
          </div>
        </div>
      </div>
    </div>
    <% } else if( sportId == 2 ){%>
    <div class="hero overlay" style="background-image: url('images/basketball sfondo.png');">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-5 mx-auto text-center">
            <h1 class="text-white">Basketball</h1>
            <p>Qui sotto troverete i tornei attivi, dove per parteciparvi è necessario creare un account e registrare la tua squadra</p>
           
          </div>
        </div>
      </div>
    </div>
        <% } else {%>
      <div class="hero overlay" style="background-image: url('images/pallavolo sfondo.png');">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-5 mx-auto text-center">
            <h1 class="text-white">Pallavolo</h1>
            <p>Qui sotto troverete i tornei attivi, dove per parteciparvi è necessario creare un account e registrare la tua squadra</p>
           
          </div>
        </div>
      </div>
    </div>
    <% } %>

    
    <div class="site-section bg-dark">
  	<div class="container">
 


        <div class="row">
          <div class="col-12 title-section">
            <h2 class="heading">Tornei disponibili</h2>
          </div>
          
          <%
          	if(tornei != null && !tornei.isEmpty()){
          		for (Torneo t : tornei){
          			String dettaglioURL = "DettaglioTorneo?id=" + t.getId() + "&sport_id=" + sportId;
          			String logoURL = "LogoTornei?=" + t.getId();
          		
          		
          	
          
          %>
          <div class="col-lg-6 mb-4">
            <div class="bg-light p-2 rounded">
              <div class="widget-body">
                  <div class="widget-vs">
                    <div class="d-flex align-items-center justify-content-around justify-content-between w-100">
                      <div class="team-1 text-center">
                      <a href="<%= dettaglioURL %>">
                      <img src="images/<%= t.getLogo() %>" 
                       alt="Logo <%= t.getNome() %>" 
                       class="img-fluid mb-2"/>
                        <h3><%= t.getNome() %></h3>
                        </a>
                        
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