<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= "it.unirc.txw.progetto.beans.squadra.*"%>
        <%@ page import= "it.unirc.txw.progetto.beans.torneo.*"%>
    
<%@ page import="java.util.Vector"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <% Torneo torneo = (Torneo) request.getAttribute("torneo"); %>
	
  <title>Squadre di <%= torneo.getNome() %> - Sports no Sekai</title>
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



</head>

<body>

<!-- Navigation-->
        <%@ include file="frame_navigation_admin_gestione_torneo.html" %>
        
        <%  
      	Vector<Squadra> squadre =  (Vector<Squadra>) request.getAttribute("squadre");
      	int torneoId = (Integer) request.getAttribute("torneo_id");
       

      	
      	String TorneoId = "DettaglioTorneo?id=" + torneo;
      

      
      %>
        

    <div class="hero text-center py-5 bg-light mt-custom">
  <div class="container">
    <!-- Logo grande al centro -->
    <img src="/images/<%= torneo.getLogo() %>" 
         alt="Logo <%= torneo.getNome() %>" 
         class="img-fluid mb-4" 
         style="max-height: 400px;">

    <!-- Nome torneo -->
    <h1 class="display-4"><%= torneo.getNome() %></h1>
    <p class="lead text-muted"> il torneo migliore al mondo</p>
  </div>
</div>

    
    
    
  

    
	<div class="site-section bg-dark">
  <div class="container">
  

  
  
  
  
    <div class="row">
      <div class="col-12 title-section">
        <h2 class="heading"> Squadre partecipanti</h2>
      </div>
      
      
      
      		<table class="table custom-table">
				  <thead>
				    <tr>
				      <th scope="col" ><h4>Logo</h4></th>
				      <th scope="col"> <h4>Nome</h4>  </th>
				      <th scope="col"> <h4>Azioni</h4></th>
				      
				      
				    </tr>
				  </thead>
      
      
      <%
      	if(squadre != null && !squadre.isEmpty()){
      		for ( Squadra s : squadre){
      			

      			
      			String modificaURL = "RichiediModificaSquadra?id=" + s.getId() ;
      			String eliminaURL = "EliminaSquadra?id=" + s.getId() ;
      		
      	
      
      
      %>

         <tbody>
				  
				  
				    <tr>
				      
				      <td> 
                    <img src="/images/<%= s.getLogo() %>" alt=" Logo <%= s.getNome() %>" class="img-fluid mb-2" style="max-height: 90px"> 
                     </td>
				      <td> <%= s.getNome() %>  </td>
				      <td> <a href="<%=modificaURL%>"><button class="btn btn-primary">MODIFICA</button></a>
			  				<a href="<%=eliminaURL%>"><button class="btn btn-primary">ELIMINA</button></a>
			  		</td>
				    </tr>
				    
				  </tbody>
  			<%
      		}
      	} else {
      		
      	
  			
  			%>
  			<div class="col-12">
      			<div class="alert alert-info text-center">
        		Al momento non ci sono squadre del torneo disponibili.
      		</div>
    		</div>
  			<%
   				 }
 			 %>
  
				</table>
				
				</div>
				</div>
			
    
    
   



       <!-- Foot-->
        <%@ include file="../foot.html" %>
  



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