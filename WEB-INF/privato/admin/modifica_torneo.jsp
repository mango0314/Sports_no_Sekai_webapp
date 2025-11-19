<%@page import="it.unirc.txw.progetto.beans.torneo.Torneo"%>
<%@page import="it.unirc.txw.progetto.beans.sport.*"%>

<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
			
			<% Torneo torneo = (Torneo) request.getAttribute("torneo"); %>

<% if (torneo !=null ){ %>
  <title>Modifica <%= torneo.getNome() %> - Sports no Sekai</title>
  <%} else{ %>
    <title>Aggiungi Torneo - Sports no Sekai</title>
    <% } %>
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

		

	<% Vector<Sport> lista_sport = (Vector<Sport>) request.getAttribute("lista_sport"); 
	 String errore = (String) request.getAttribute("errore");%>
	 
	<%  if( errore != null){ %>
		 <div class="alert alert-danger">
	        <ul>
	            
	                   <li><%= errore %></li>
	          
	        </ul>
	    </div>
	 <% } %>
	
	
 <%
	


	String action="AggiungiTorneo";
	String logo="";
	String nome="";
	
	String sport="";
	

	if (torneo!=null){
		action="ModificaTorneo";
		
	            nome = torneo.getNome()+"";
		 
	}
%>
	
	
	
	
  <!-- Navigation-->
    <!-- frame_logo.html -->
<div class="logo-header text-center py-4">
  <a href="/">
    <img src="../images/Sports no Sekai logo no sfondo.png" alt="Sports no Sekai" class="logo img-fluid" />
  </a>
</div>
    

<section class="login-hero d-flex align-items-center justify-content-center">
  <div class="container">
    <div class="row justify-content-center">
       <div class="col-12 col-sm-10 col-md-8 col-lg-6">

        <h1 class="text-center mb-4 text-white">Gestione Tornei</h1>

        <form id="contactForm" method="post" action="<%= action %>">
          <div class="form-floating mb-3">
            <input class="form-control" id="nome" name="nome_torneo" type="text"
                       
                placeholder="<%=nome %>" 
                    data-sb-validations="required" required           
                  
                   value="<%=nome %>"> 
            <div class="invalid-feedback" data-sb-feedback="name:required">
              Nome is required.
            </div>
          </div>
         
               
           
            
          
      <%
      
      if( lista_sport != null){
    	  
      %>
          
          <div class="form-floating mb-3">
    <select
      class="custom-select"
      id="sport"
      name="sport"
      required
 
    >
      <option value="" disabled selected>Seleziona lo sport</option>

      <%
      
    
      String nome_sport = null;

      for ( Sport s : lista_sport){
          if (s.getId() == 1) {
                nome_sport = "Calcio";
            } else if (s.getId() == 2) {

                nome_sport = "Basketball";

            } else {
                nome_sport = "Pallavolo";
            }


          %>

      <option value= "<%= s.getId() %>"> <%= nome_sport %></option>
        
    <% }
    %> 
      
       </select>

    
  </div>
      
     <% } %>   
      
      
     

    
 



  
  <% if (torneo == null){ %>
  			<div class="row">
        <!-- Clicca qui le tue immagini -->
        <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">
          <input type="radio" id="logo1" name="teamLogo" value="logo_1.png" required>
          <label for="logo1" class="card p-2 text-center">
            <img src="/images/logo_1.png" class="img-fluid" alt="Logo 1">
            <div class="mt-2">Logo 1</div>
          </label>
        </div>
        <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">
          <input type="radio" id="logo2" name="teamLogo" value="logo_2.png" required>
          <label for="logo2" class="card p-2 text-center">
            <img src="/images/logo_2.png" class="img-fluid" alt="Logo 2">
            <div class="mt-2">Logo 2</div>
            </label>
            </div>
            <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">
           <input type="radio" id="logo3" name="teamLogo" value="logo_3.png" required >
          <label for="logo3" class="card p-2 text-center">
            <img src="/images/logo_3.png" class="img-fluid" alt="Logo 3">
            <div class="mt-2">Logo 3</div>
          </label> 
          </div>
           <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">        
           <input type="radio" id="logo4" name="teamLogo" value="logo_4.png" required>
          <label for="logo4" class="card p-2 text-center">
            <img src="/images/logo_4.png" class="img-fluid" alt="Logo 4">
            <div class="mt-2">Logo 4</div>
          </label>
        </div>
        
        <div class="invalid-feedback">
      Devi selezionare un logo.
    </div>
    </div>
    
        <!-- Aggiungi altri logo-option come sopra -->
      
      
      <% } %>
      
        
          
          
          
          <button class="btn btn-primary btn-lg btn-block" id="submitButton" type="submit">
            Invia
          </button>
          
           
        </form>
        
        <br>
         <br>
         <br>

      </div>
    </div>
  </div>
</section>





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
  <script>
  				function selezionaVoce(testo) {
   				 // Cambia il testo del bottone principale
    			document.getElementById("dropdownBtn").innerText = testo;

    				// (Facoltativo) Invia la scelta alla servlet
    				// fetch('MiaServlet', { ... })
  					}
			</script>
			
			
<script>
  document.querySelectorAll('.data-nascita').forEach(function(input) {
    const feedback = input.parentNode.querySelector('.age-feedback');

    input.addEventListener('input', function(e) {
      // 1) Rimuovo tutto tranne le cifre
      let digits = input.value.replace(/\D/g, '');
      // 2) Limito a 8 cifre (2gg+2mm+4aaaa)
      if (digits.length > 8) {
        digits = digits.slice(0, 8);
      }
      // 3) Inserisco automaticamente gli slash
      let formatted = '';
      if (digits.length > 4) {
        formatted = digits.slice(0,2) + '/' +
                    digits.slice(2,4) + '/' +
                    digits.slice(4);
      } else if (digits.length > 2) {
        formatted = digits.slice(0,2) + '/' +
                    digits.slice(2);
      } else {
        formatted = digits;
      }
      input.value = formatted;

      // 4) Resetto eventuali valid/invalid state
      feedback.style.display = 'none';
      input.classList.remove('is-invalid', 'is-valid');
    });

    input.addEventListener('keydown', function(e) {
      // 5) Se è già pieno (10 char) e non è Backspace o Delete, impedisco
      const allowed = [8, 46]; // backspace, delete
      if (input.value.length >= 10 && !allowed.includes(e.keyCode)) {
        e.preventDefault();
      }
    });

    input.addEventListener('blur', function() {
      const val = input.value.trim();
      // parsing
      const parts = val.split('/');
      let dob = null;
      if (parts.length === 3) {
        const d = parseInt(parts[0],10),
              m = parseInt(parts[1],10) - 1,
              y = parseInt(parts[2],10);
        const dt = new Date(y, m, d);
        if (dt.getFullYear()===y && dt.getMonth()===m && dt.getDate()===d) {
          dob = dt;
        }
      }
      if (!dob) {
        feedback.textContent = 'Campo non valido';
        showError();
        return;
      }
      // calcolo età
      const today = new Date();
      let age = today.getFullYear() - dob.getFullYear();
      const mm = today.getMonth() - dob.getMonth();
      if (mm < 0 || (mm === 0 && today.getDate() < dob.getDate())) age--;
      if (age < 18 || age > 60) {
        feedback.textContent = 'Età non valida: hai ' + age + ' anni';
        showError();
      } else {
        feedback.style.display = 'none';
        input.classList.remove('is-invalid');
        input.classList.add('is-valid');
      }
    });

    function showError() {
      feedback.style.display = 'block';
      input.classList.add('is-invalid');
    }
  });
</script>

			
			
			
			
			

<script>
    // Raccoglie tutti i select
    const selects = Array.from(document.querySelectorAll('.jersey-select'));

    function aggiornaOpzioni() {
      // Numeri già scelti
      const scelte = selects
        .map(sel => sel.value)
        .filter(v => v !== "");

      selects.forEach(sel => {
        const corrente = sel.value;
        sel.querySelectorAll('option').forEach(opt => {
          if (opt.value === "") {
            opt.disabled = false; // placeholder sempre abilitato
          } else if (opt.value === corrente) {
            opt.disabled = false; // la scelta corrente resta abilitata qui
          } else if (scelte.includes(opt.value)) {
            opt.disabled = true;  // disabilita se preso altrove
          } else {
            opt.disabled = false; // altrimenti abilita
          }
        });
      });
    }

    // Associa l'evento change a ciascun select
    selects.forEach(sel => {
      sel.addEventListener('change', aggiornaOpzioni);
    });

    // Inizializza
    aggiornaOpzioni();
  </script>
  
  <script>
  document.addEventListener('DOMContentLoaded', () => {
    const inputs = document.querySelectorAll('input[name^="nome_"], input[name^="cognome_"]');
    inputs.forEach(input => {
      input.addEventListener('input', () => {
        input.value = input.value.replace(/[^A-Za-zÀ-ÿ\s']/g, '');
      });
    });
  });
</script>


  <script src="js/main.js"></script>

</body>

</html>