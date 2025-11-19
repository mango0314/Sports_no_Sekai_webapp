<%@page import="it.unirc.txw.progetto.servlet.DettaglioSquadra"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= "it.unirc.txw.progetto.beans.scontro.*"%>
        <%@ page import= "it.unirc.txw.progetto.beans.torneo.*"%>
                <%@ page import= "it.unirc.txw.progetto.beans.squadra.*"%>
    <%@page import="java.text.SimpleDateFormat"%>     
     <%@ page import="java.sql.Time, java.text.SimpleDateFormat" %>     
        
    
<%@ page import="java.util.Vector"%>
<!DOCTYPE html>
<html lang="en">

<head>

<% Squadra squadra = (Squadra) request.getAttribute("squadra"); %>


  <title> Scontri - Sports no Sekai</title>
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
        <%@ include file="frame_navigation_utente.html" %>
        
        <%  
      	Vector<Scontro> scontri =  (Vector<Scontro>) request.getAttribute("scontri");
      	
       
        Scontro ultimo_scontro = (Scontro) request.getAttribute("ultimo_scontro");
          	 	
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // creo l'oggetto


      
      %>
        


    <div class="hero text-center py-5 bg-light mt-custom">
  <div class="container">
    <!-- Logo grande al centro -->
    <img src="../images/<%= squadra.getLogo() %>" 
         alt="Logo <%= squadra.getNome() %>" 
         class="img-fluid mb-4" 
         style="max-height: 400px;">

    <!-- Nome torneo -->
    <h1 class="display-4"><%= squadra.getNome() %></h1>
    
  </div>
</div>


<% 
	if(ultimo_scontro != null ){
      		
      	
      	String NomeTeam1_ultimo = new SquadraDAO().getbyId(ultimo_scontro.getSquadra1_id()).getNome();
		String NomeTeam2_ultimo = new SquadraDAO().getbyId(ultimo_scontro.getSquadra2_id()).getNome();
		String LogoTeam1_ultimo = new SquadraDAO().getbyId(ultimo_scontro.getSquadra1_id()).getLogo();
			String LogoTeam2_ultimo = new SquadraDAO().getbyId(ultimo_scontro.getSquadra2_id()).getLogo();


      
      %>

    
    
    <div class="container">
      

      <div class="row">
        <div class="col-lg-12">
        
       
          
          <div class="d-flex team-vs">
            <span class="score"> <%= ultimo_scontro.getPunteggio1() %> - <%= ultimo_scontro.getPunteggio2() %> </span>
            <div class="team-1 w-50">
              <div class="team-details w-100 text-center">
              
                <img src="../images/<%= LogoTeam1_ultimo %>" alt="Image" class="img-fluid">
                <h3> <%= NomeTeam1_ultimo %> </h3>
                
              </div>
            </div>
            <div class="team-2 w-50">
              <div class="team-details w-100 text-center">
              
              
                <img src="../images/<%= LogoTeam2_ultimo  %>" alt="Image" class="img-fluid">
                <h3><%= NomeTeam2_ultimo %> </h3>
               
              </div>
            </div>
          </div>
          
          	<%
      		
      	} else {
      		
      	
  			
  			%>
  				<div class="col-12">
      			<div class="alert alert-info text-center">
        		Al momento non c'è l'ultimo scontro del torneo disponibili.
      		</div>
    		</div>
  			<%
   				 }
 			 %>

          
       </div>   
      </div> 
      
    </div>
    
      

    
	<div class="site-section bg-dark">
  <div class="container">
  

  
  
  
  
    <div class="row">
      <div class="col-12 title-section">
        <h2 class="heading">Prossimi scontri</h2>
      </div>
      
      
      <%
      	if(scontri != null && !scontri.isEmpty()){
      		for ( Scontro s : scontri){
      			if( s.getPunteggio1() != null ){
      				continue;
      			}
      			
      			

      			String NomeTeam1 = new SquadraDAO().getbyId(s.getSquadra1_id()).getNome();
      			String NomeTeam2 = new SquadraDAO().getbyId(s.getSquadra2_id()).getNome();
      			String LogoTeam1 = new SquadraDAO().getbyId(s.getSquadra1_id()).getLogo();
      			String LogoTeam2 = new SquadraDAO().getbyId(s.getSquadra2_id()).getLogo();
      			String dataStr = sdf.format(s.getData()); 

      	
      
      
      %>

        <div class="col-lg-6 mb-4">
          <div class="bg-light p-4 rounded">
            <div class="widget-body">
              <div class="widget-vs">
                <div class="d-flex align-items-center justify-content-around justify-content-between w-100">
                  
                  <!-- Squadra 1 -->
                  <div class="team-1 text-center">
                 
                    <img src="../images/<%= LogoTeam1 %>" alt=" Logo <%= NomeTeam1 %>" class="img-fluid mb-2">
                    <h3><%= NomeTeam1 %></h3>
                     
                  </div>
                  
                  <!-- VS -->
                  <div>
                    <span class="vs"><span>VS</span></span>
                  </div>
                  
                  <!-- Squadra 2 -->
                  <div class="team-2 text-center">
                
                    <img src="../images/<%= LogoTeam2 %>" alt="Logo <%= NomeTeam2 %>">
                    <h3><%= NomeTeam2 %></h3>
                    
                  </div>
                </div>
              </div>
            </div>

            <div class="text-center widget-vs-contents mb-4">
                           
              <p class="mb-5">
                <span class="d-block"><%= dataStr %></span>
                <span class="d-block"><%= s.getOrario() %></span>
              </p>
            </div>
          </div>
        </div>
    
  			<%
      		}
      	} else {
      		
      	
  			
  			%>
  				<div class="col-12">
      			<div class="alert alert-info text-center">
        		Al momento non ci sono scontri del torneo disponibili.
      		</div>
    		</div>
  			<%
   				 }
 			 %>
  
</div>
</div>
</div>


		<div class="site-section bg-dark">
  <div class="container">

		<div class="row">
      <div class="col-12 title-section">
        <h2 class="heading">Scontri disputati</h2>
      </div>
      
      
      <%
      	if(scontri != null && !scontri.isEmpty()){
      		for ( Scontro s : scontri){
      			if( s.getPunteggio1() == null ){
      				continue;
      			}
      			
      			
      			String NomeTeam1 = new SquadraDAO().getbyId(s.getSquadra1_id()).getNome();
      			String NomeTeam2 = new SquadraDAO().getbyId(s.getSquadra2_id()).getNome();
      			String LogoTeam1 = new SquadraDAO().getbyId(s.getSquadra1_id()).getLogo();
      			String LogoTeam2 = new SquadraDAO().getbyId(s.getSquadra2_id()).getLogo();
      			String dataStr = sdf.format(s.getData()); 

      	
      
      
      %>

        <div class="col-lg-6 mb-4">
          <div class="bg-light p-4 rounded">
            <div class="widget-body">
              <div class="widget-vs">
                <div class="d-flex align-items-center justify-content-around justify-content-between w-100">
                  
                  <!-- Squadra 1 -->
                  <div class="team-1 text-center">
                 
                    <img src="../images/<%= LogoTeam1 %>" alt=" Logo <%= NomeTeam1 %>" class="img-fluid mb-2">
                    <h3><%= NomeTeam1 %></h3>
                     
                  </div>
                  
                  <!-- VS -->
                  <div>
						<span class="score"><%=s.getPunteggio1() %> - <%= s.getPunteggio2() %></span>                  
                  </div>
                  
                  <!-- Squadra 2 -->
                  <div class="team-2 text-center">
                 
                    <img src="../images/<%= LogoTeam2 %>" alt="Logo <%= NomeTeam2 %>">
                    <h3><%= NomeTeam2 %></h3>
                   
                  </div>
                </div>
              </div>
            </div>

            <div class="text-center widget-vs-contents mb-4">
                           
              <p class="mb-5">
                <span class="d-block"><%= dataStr %></span>
                <span class="d-block"><%= s.getOrario() %></span>
              </p>
            </div>
          </div>
        </div>
    
  			<%
      		}
      	} else {
      		
      	
  			
  			%>
  				<div class="col-12">
      			<div class="alert alert-info text-center">
        		Al momento non ci sono scontri del torneo già disputati.
      		</div>
    		</div>
  			<%
   				 }
 			 %>
  
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