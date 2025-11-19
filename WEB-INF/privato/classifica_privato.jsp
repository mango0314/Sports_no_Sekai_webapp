<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "it.unirc.txw.progetto.beans.squadra.*"%>
<%@ page import= "it.unirc.txw.progetto.beans.torneo.*"%>
<%@ page import= "it.unirc.txw.progetto.beans.classifica.*"%>
        
    
<%@ page import="java.util.Vector"%>
<!DOCTYPE html>
<html lang="en">

<head>

	<% int ruolo = ( int ) session.getAttribute("ruolo"); %>

	
	
  <title>Classifica - Sports no Sekai</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Favicon-->
<link rel="../apple-touch-icon" sizes="180x180"
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
        <% 
        
        if( ruolo==1) {
        	%>
        <%@ include file="utente/frame_navigation_utente.html"%>
        
        <%
        }else{
       	
       	%>
       	
       	<%@ include file="admin/frame_navigation_admin_gestione_torneo.html"%>
       	
       	<%
        }
       	%>
       	
        <%  
      	Vector<Classifica> classifica =  (Vector<Classifica>) request.getAttribute("classifica");
      	int torneoId = (Integer) session.getAttribute("torneo_id");
        Torneo torneo = (Torneo) request.getAttribute("torneo");
      	int SportId = (Integer) request.getAttribute("sport_id");

        

      	
      	String TorneoId = "DettaglioTorneo?id=" + torneo;
      

      
      %>
        

    <div class="hero text-center py-5 bg-light mt-custom">
  <div class="container">
    <!-- Logo grande al centro -->
    <img src="../images/<%= torneo.getLogo() %>" 
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
             <h2 class="heading">Classifica</h2>
             <% 
             if(classifica != null && !classifica.isEmpty()){
                    
             String modificaURL = "RichiediModificaClassifica?id=" + torneo.getId() + "&sport_id=" + torneo.getSport_id();
                    if ( ruolo==0) { %>
        <a 
					    href="<%=modificaURL%>"
					    class="btn btn-primary btn-sm ml-3" 
					    title="Aggiungi Scontri"
					  >
					    <button class="btn btn-primary">MODIFICA</button>
					  </a>
					  <% } %>
        
                    
            </div>
              <table class="table custom-table">
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
                    
                    
                    
                  </tr>
                </thead>
                
        <%
      	
      		int posizione = 0;
      		for ( Classifica c : classifica){
      			String DettaglioSquadra = "DettaglioSquadra?id=" + c.getId_squadra() + "&torneo_id=" + c.getTorneo_id();
      			
      			String TeamLogo = new SquadraDAO().getbyId(c.getId_squadra()).getLogo();
      			
      			String NomeTeam = new SquadraDAO().getbyId(c.getId_squadra()).getNome();
      		
      			posizione ++; 
      				
      				
      			
      					
      		
      	
      
      
      %>
                
                
                <tbody>
                  <tr>
                    <td><%= posizione %></td>
                    <td> <a href = "<%= DettaglioSquadra %>">
                    <img src="../images/<%= TeamLogo %>" alt=" Logo <%= NomeTeam %>" class="img-fluid mb-2"  style="max-height: 60px">
                            <%=   NomeTeam %> </a>
                     </td>
                    <td> <h4> <%= c.getVittorie() %></h4> </td>
                    
                    
                    <% if ( SportId == 1)  {%>
                                       
                    
                    <td> <%= c.getPareggi() %></td>
                    
                    <% } %>
                    
                    <td> <%= c.getSconfitte() %></td>
                    
                     <td> <%= c.getMedia() %></td> 
                    <td> <%= c.getPunteggioTotale() %> </td>
                  </tr>
           
                </tbody>
                
                		<%
      			
      		}
      	}
      	else{
      		
      	
  			
  			%>
  				<div class="col-12">
      			<div class="alert alert-info text-center">
        		La classifica non è ancora pronta.
      		</div>
    		</div>
  			<%
   				 }
 			 %>
  
				</table>
				
				</div>
				</div>
             
            </div>

          
        
       <!-- .site-section -->
    

   

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