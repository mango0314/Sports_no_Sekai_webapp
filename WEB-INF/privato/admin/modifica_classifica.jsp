<%@page import="org.apache.tomcat.util.collections.SynchronizedQueue"%>
<%@page import="it.unirc.txw.progetto.beans.squadra.*"%>
<%@page import="it.unirc.txw.progetto.beans.classifica.Classifica"%>
<%@page import="it.unirc.txw.progetto.beans.torneo.Torneo"%>
<%@page import="it.unirc.txw.progetto.beans.sport.*"%>

<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
			
			<% Vector<Classifica> classifica = (Vector<Classifica>) request.getAttribute("classifica");
			int SportId = (int) session.getAttribute("sport_id");%>


  <title>Modifica Classifica  - Sports no Sekai</title>
  
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
    <!-- frame_logo.html -->
<div class="logo-header text-center py-4">
  <a href="/">
    <img src="../images/Sports no Sekai logo no sfondo.png" alt="Sports no Sekai" class="logo img-fluid" />
  </a>
</div>
    


  <div class="site-section bg-dark">
      <div class="container">
        <div class="row justify-content-center">
          
            
            
            
              
  <form id="contactForm" method="post" action="ModificaClassifica">
         <table class="table custom-table">
         <caption class="text-center text-white h3 mb-4" 
             style="caption-side: top;">
     <h1>Gestione Classifica</h1> 
    </caption>
  		<thead>
                    <tr>
                    	 <th>P</th>
                    <th>Squadre</th>
                    <th>W</th>
                    <%
                    if(SportId == 1){
                    	
                                   
                    %>
                    <th>D</th>
                    
                    <%
                    }
                    %>
                    <th>L</th>
                    
                     <%
                    if(SportId == 1){
                    	
                                   
                    %>
                    <th> G/P </th>
                    
                    <%
                    }else if( SportId == 2){
                    	
                    
                    %>
                    <th> P/P </th>
                    <%
                    } else{
                    %>
                    <th> S/P </th>
                    <%} %>
                    <th>PTS</th>
                    <th>M/B</th>
                    </tr>
                </thead>
  	<tbody>
                    <% 
                      int posizione = 0;
                      if (classifica != null && !classifica.isEmpty()) {
                        for (Classifica c : classifica) {
                          posizione++;
                          String NomeTeam = new SquadraDAO()
                                              .getbyId(c.getId_squadra())
                                              .getNome();
                          String TeamLogo = new SquadraDAO()
                                              .getbyId(c.getId_squadra())
                                              .getLogo();
                    %>
                    <tr>
                      <!-- P -->
                      <td><%= posizione %></td>

                      <!-- Squadre -->
                      <td>
                        <img src="../images/<%= TeamLogo %>" 
                             alt="Logo <%= NomeTeam %>" 
                             style="max-height:60px" class="img-fluid mb-2"/>
                        <%= NomeTeam %>
                      </td>

                      <!-- W -->
                      <td><%= c.getVittorie() %></td>

                      <!-- D (solo se SportId==1) -->
                      <% if (SportId == 1) { %>
                        <td><%= c.getPareggi() %></td>
                      <% } %>

                      <!-- L -->
                      <td><%= c.getSconfitte() %></td>

                      <!-- G/P o P/P o S/P -->
                      <td><%= c.getMedia() %></td>

                      <!-- PTS modificabile -->
                      <td>
                        <%= c.getPunteggio() %>
                        
                      </td>
                      <td>
                      <input type="hidden" name="id_squadra[]" 
                               value="<%= c.getId_squadra() %>"/>
                      <input type="number" name="bonus[]" 
                               value="<%= c.getBonus() %>" 
                               min="-100" class="form-control" required/>
                       </td>
                    </tr>
                    <%      }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">Nessun dato di classifica disponibile</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <button type="submit" class="btn btn-primary">Salva Modifiche</button>
		</form>
   	 </div>
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