<%@page import="it.unirc.txw.progetto.beans.scontro.*"%>
<%@page import="it.unirc.txw.progetto.beans.squadra.*"%>

<%@ page import="java.sql.Time" %>


<%@page import="it.unirc.txw.progetto.beans.sport.*"%>

<%@page import="java.util.Vector"%>
<%@page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
			
			<% Scontro scontro = (Scontro) request.getAttribute("scontro"); %>

<% if (scontro !=null ){ %>
  <title>Modifica - Sports no Sekai</title>
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
<!-- jQuery (se non già incluso) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Timepicker plugin compatibile con Bootstrap 4 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>




</head>

<body>

		

	<% Vector<Squadra> lista_squadretorneo = (Vector<Squadra>) request.getAttribute("lista_squadretorneo"); 
	 Vector<String> lista_errori = (Vector<String>) request.getAttribute("errori");
	 int sportId = (int) session.getAttribute("sport_id");%>
	
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
	
 <%
	


	String action="AggiungiScontro";
	int scontro_id = -1;
	String nomeSquadra1="";
	String nomeSquadra2="";
	Integer punteggio1 = null;
	Integer punteggio2 = null;
	Date data = null;
	Time orario = null;
	
	

	

	if (scontro!=null){
		action="ModificaScontro";
		scontro_id = scontro.getId();
		 nomeSquadra1= new ScontroDAO().getNomesquadra(scontro.getSquadra1_id());
		 nomeSquadra2= new ScontroDAO().getNomesquadra(scontro.getSquadra2_id());
		 punteggio1 = scontro.getPunteggio1();
		 punteggio2 = scontro.getPunteggio2();
		 data = scontro.getData();
		 orario = scontro.getOrario();
		 
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

        <h1 class="text-center mb-4 text-white">Gestione Scontro</h1>

        <form id="contactForm" method="post" action="<%= action %>">
				        <% if (scontro != null) { %>
				  <input type="hidden" 
				         name="scontro_id" 
				         value="<%= scontro_id %>" />
						<% } %>
         <div class="form-floating mb-3">
								  <select
								    class="custom-select"
								    id="squadra1id"
								    name="squadra1id"
								    required
								  >
								    <option value="" disabled <%= (scontro==null)?"selected":"" %>>
								      Seleziona la squadra in casa
								    </option>
								  <% for (Squadra s : lista_squadretorneo) { %>
								    <option
								      value="<%= s.getId() %>"
								      <%= (scontro!=null && scontro.getSquadra1_id()==s.getId()) ? "selected" : "" %>
								    >
								      <%= s.getNome() %>
								    </option>
								  <% } %>
								  </select>
								</div>
								
								<div class="form-floating mb-3">
								  <select
								    class="custom-select"
								    id="squadra2id"
								    name="squadra2id"
								    required
								  >
								    <option value="" disabled <%= (scontro==null)?"selected":"" %>>
								      Seleziona la squadra in trasferta
								    </option>
								  <% for (Squadra s : lista_squadretorneo) { %>
								    <option
								      value="<%= s.getId() %>"
								      <%= (scontro!=null && scontro.getSquadra2_id()==s.getId()) ? "selected" : "" %>
								    >
								      <%= s.getNome() %>
								    </option>
								  <% } %>
								  </select>
								</div>
								
													  
					  
					  
					  
					  
					  <%  String maxAttr = (sportId == 3) ? "max=\"3\"" : ""; %>
					    <div class="row">
						    <div class="col">
						      <label for="punteggio1">Punteggio 1</label>
						      <input type="number" id="punteggio1" name="punteggio1" class="form-control" min="0"
						      step="1"
						        <%= maxAttr %>
      							oninput="if (this.value < 0) this.value = 0;"
						             value="<%= (punteggio1 != null ? punteggio1 : "") %>"/>
 
						    </div>
						    <div class="col">
						      <label for="punteggio2">Punteggio 2</label>
						      <input type="number" id="punteggio2" name="punteggio2" class="form-control" min="0"
						      step="1"
						        <%= maxAttr %>
      							oninput="if (this.value < 0) this.value = 0;"
										value="<%= (punteggio2 != null ? punteggio2 : "") %>"/>

						    </div>
						  </div>
						
						  <div class="form-group mt-3">
						    <label for="data">Data Scontro</label>
						    <input type="date" id="data" name="data" class="form-control"
						           value="<%=
						             (data!=null)
						               ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(data)
						               : ""
						           %>"/>
						  </div>
						
						  <div class="form-group mt-2">
						    <label for="orario">Orario</label>
						    <input
								  type="time"
								  id="orario"
								  name="orario"
								  class="form-control"
								  placeholder="HH:mm"
								  pattern="^([01]\\d|2[0-3]):([0-5]\\d)$"
								  value="<%= (orario != null ? orario : "") %>"
								  required
								/>

						  </div>
		          
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
  $(function() {
	  $('#orario').timepicker({
		  timeFormat: 'HH:mm',
		  interval: 15,        // ogni 15 minuti
		  dynamic: false,
		  dropdown: true,
		  scrollbar: true,
		  showMinutes: true,   // assicura che mostri i minuti
		  showSeconds: false   // nessun secondo
		});

    
    
    

   
  });
 
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