<%@page import="it.unirc.txw.progetto.beans.torneo.Torneo"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <title>Registrazione - Sports no Sekai</title>
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

	<% Vector<Torneo> lista_tornei = (Vector<Torneo>) request.getAttribute("lista tornei"); %>
		<% Vector<String> lista_errori = (Vector<String>) request.getAttribute("errori"); %>
	
	
	<% if (lista_errori != null && !lista_errori.isEmpty()) { %>
    <div class="alert alert-danger">
        <ul>
            <% 
               // Iterazione “alla vecchia scuola”
               for (int i = 0; i < lista_errori.size(); i++) { 
                   String messaggio = lista_errori.get(i);
            %>
                   <li><%= messaggio %></li>
            <% } %>
        </ul>
    </div>
<% } %>
	
  <!-- Navigation-->
    <!-- frame_logo.html -->
<div class="logo-header text-center py-4">
  <a href="/">
    <img src="images/Sports no Sekai logo no sfondo.png" alt="Sports no Sekai" class="logo img-fluid" />
  </a>
</div>
    

<section class="login-hero d-flex align-items-center justify-content-center">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-7">

        <h1 class="text-center mb-4 text-white">Registrati</h1>
        
        
		<h5 class="text-left mb-4 text-white">Dati di accesso</h5>
        <form id="contactForm" method="post" action="Registrazione">
          <div class="form-floating mb-3">
            <input class="form-control" id="username" name="username" type="text"
                   placeholder="Username" data-sb-validations="required" required/>
            <div class="invalid-feedback" data-sb-feedback="name:required">
              Username is required.
            </div>
          </div>
          <div class="form-floating mb-3">
				  <input 
				    class="form-control" 
				    id="password" 
				    name="password" 
				    type="password"
				    placeholder="Password"
				    required 
				    minlength="8"
				    pattern="^(?=.*[!@#$%^&*]).{8,}$"
				    title="Minimo 8 caratteri e almeno un carattere speciale (!@#$%^&*)" /> 
				  
				  
				  <div class="invalid-feedback">
				    La password deve avere almeno 8 caratteri e contenere un carattere speciale (!@#$%^&*).
				  </div>
				</div>
               
           
            
          		<h5 class="text-left mb-4 text-white">Dati squadra</h5>
          
                <div class="form-floating mb-3">
            <input class="form-control" id="nome" name="nomesquadra" type="text"
                   placeholder="Nome squadra" data-sb-validations="required" required/>
            <div class="invalid-feedback" data-sb-feedback="name:required">
              Nome squadra is required.
            </div>
          </div>
          
          <div class="form-floating mb-3">
    <select
      class="custom-select"
      id="torneo"
      name="torneo"
      required
 
    >
      <option value="" disabled selected>Seleziona il torneo</option>

      <%
      String sport = null;

      for ( Torneo t : lista_tornei){
          if (t.getSport_id() == 1) {
                sport = "Calcio";
            } else if (t.getSport_id() == 2) {

                sport = "Basketball";

            } else {
                sport = "Pallavolo";
            }


          %>

      <option value= "<%= t.getId() %>"> <%= t.getNome()%> - <%= sport %></option>

      <% 

       } %>
    </select>

    
  </div>
  
  
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
        <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">        
           <input type="radio" id="logo5" name="teamLogo" value="logo_5.png" required>
          <label for="logo5" class="card p-2 text-center">
            <img src="/images/logo_5.png" class="img-fluid" alt="Logo 5">
            <div class="mt-2">Logo 5</div>
          </label>
        </div>
        <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">        
           <input type="radio" id="logo6" name="teamLogo" value="logo_6.png" required>
          <label for="logo6" class="card p-2 text-center">
            <img src="/images/logo_6.png" class="img-fluid" alt="Logo 6">
            <div class="mt-2">Logo 6</div>
          </label>
        </div>
        <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">        
           <input type="radio" id="logo7" name="teamLogo" value="logo_7.png" required>
          <label for="logo7" class="card p-2 text-center">
            <img src="/images/logo_7.png" class="img-fluid" alt="Logo 7">
            <div class="mt-2">Logo 7</div>
          </label>
        </div>
        <div class="col-6 col-md-4 col-lg-3 logo-option mb-3">        
           <input type="radio" id="logo8" name="teamLogo" value="logo_8.png" required>
          <label for="logo8" class="card p-2 text-center">
            <img src="/images/logo_8.png" class="img-fluid" alt="Logo 8">
            <div class="mt-2">Logo 8</div>
          </label>
        </div>
        
        <div class="invalid-feedback">
      Devi selezionare un logo.
    </div>
        <!-- Aggiungi altri logo-option come sopra -->
      </div>
      
      
      <div class="container py-4">
    <h5 class="mb-4"><strong>Componenti squadra</strong></h5>
    <table class="table table-striped table-bordered">
      <thead class="thead-dark">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Nome</th>
          <th scope="col">Cognome</th>
          <th scope="col">Data di nascita</th>
          <th scope="col">Numero di maglia</th>
         
        </tr>
      </thead>
      <tbody>
      <% for (int i = 1; i <= 7; i++) { %>
            <tr>
              <th scope="row" class="text-white"><%= i %></th>
              <td>
                <input type="text" name="nome_<%= i %>"
                       class="form-control form-control-sm" 
                       pattern="[A-Za-zÀ-ÿ\s']+" 
       title="Il nome non può contenere numeri o simboli speciali"
                       required>
              </td>
              <td>
                <input type="text" name="cognome_<%= i %>"
                       class="form-control form-control-sm" 
                       pattern="[A-Za-zÀ-ÿ\s']+" 
       title="Il cognome non può contenere numeri o simboli speciali"
                       required>
              </td>
              <td>
                <input type="text" name="dataNascita_<%= i %>"
                       class="form-control form-control-sm data-nascita"
                       placeholder="gg/mm/aaaa"
						pattern="\d{2}/\d{2}/\d{4}"
                       title="Formato richiesto: gg/mm/aaaa"
                       required>
                         <!-- Contenitore per il messaggio dinamico -->
  						<div class="invalid-feedback age-feedback" style="display:none;">
   						 Età non valida: devi avere tra 18 e 60 anni.
  						</div>
              </td>
              <td>
                <select name="numeroMaglia_<%= i %>"
                        class="form-select form-select-sm jersey-select"
                        required>
                  <option value="" selected disabled>— Seleziona —</option>
                  <% for (int n = 1; n <= 99; n++) { %>
                    <option value="<%= n %>"><%= n %></option>
                  <% } %>
                </select>
         
              </td>
                   
            </tr>
          <% } %>
          
         </table>
                     <div class="invalid-feedback">
      Devi selezionare uno numero di maglia.
    </div>
         
         </div>
         
         
          
          
          
          
          
          
          <button class="btn btn-primary btn-xl w-100" id="submitButton" type="submit">
            Registrati
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
        <%@ include file="foot.html" %>



  </div>
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