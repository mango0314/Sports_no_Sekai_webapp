<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import= "it.unirc.txw.progetto.beans.sport.*"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <title>Sports no Sekai - Tornei di ogni tipo</title>
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
        
        

    <div class="hero overlay" style="background-image: url('images/background.png');">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-5 ml-auto">
            <h1 class="text-white">Sports No Sekai</h1>
            <p>Trasformati nel tuo anime preferito e domina il campo da gioco.</p>
            <p>
            	<a href="RichiediRegistrazione" class="btn btn-primary py-3 px-4 mr-4"> ISCRIVITI A UN TORNEO </a>
				<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#LearnMoreModal">
				LEARN MORE				

				</button>            
				</p>
				  
          </div>
        </div>
      </div>
    </div>
    
    
    
    <%
    		Vector<Sport> listaSport = (Vector<Sport>) request.getAttribute("listaSport");
    
    
    %>

    
    
   

    <div class="latest-news">
      <div class="container">
        <div class="row">
          <div class="col-12 title-section">
            <h2 class="heading">Scegli il tuo sport</h2>
          </div>
        </div>
        
        <div class="row no-gutters">
        
        <% 
        	if( listaSport != null && !listaSport.isEmpty()){
        		for( Sport s : listaSport){
        			String DettaglioSport = "DettaglioSport?id=" + s.getId();
        		
        	
        
        
        
        %>
        
        
        
        
          <div class="col-md-4">
            <div class="post-entry">
              <a href="<%= DettaglioSport %>">
              <% if(s.getId() == 1 ){ %>
                <img src="images/calcio.jpeg" alt="Image" class="img-fluid">
                <% } else if(s.getId() == 2){ %>
                <img src="images/basketball.jpg" alt="Image" class="img-fluid">
                <%} else{ %>
                <img src="images/pallavolo.jpg" alt="Image" class="img-fluid">
                <% } %>
              </a>
              <div class="caption">
                <div class="caption-inner">
                  <%
  						String raw = s.getNome();                            // es. “calcio”
  						String cap = raw.isEmpty()
              					 ? raw
               					 : raw.substring(0,1).toUpperCase()      // “C”
                				 + raw.substring(1).toLowerCase();    // “alcio”
					%>
					<h1 class="mb-3">- <%= cap %></h1>
                  
                </div>
              </div> 
            </div>
          </div>
          
          <% }
        	}
          
        	else{
        		
        	
        		%>
  				<div class="col-12">
      			<div class="alert alert-info text-center">
        		Al momento non ci sono sport disponibili.
      		</div>
    		</div>
  			<%
   				 }
 			 %>
          
          
          
        </div>

      </div>
    </div>
    


    <div class="site-section">
      <div class="container">
        <div class="row">
          <div class="col-6 title-section">
            <h2 class="heading">Videos</h2>
          </div>
          <div class="col-6 text-right">
            <div class="custom-nav">
            <a href="#" class="js-custom-prev-v2"><span class="icon-keyboard_arrow_left"></span></a>
            <span></span>
            <a href="#" class="js-custom-next-v2"><span class="icon-keyboard_arrow_right"></span></a>
            </div>
          </div>
        </div>


        <div class="owl-4-slider owl-carousel">
          <div class="item">
            <div class="video-media">
              <img src="images/immagine_video-isagi.jpg" alt="Image" class="img-fluid">
              <a href="https://www.youtube.com/watch?v=XhA9pM5_Y2E&ab_channel=AKUN%E5%BC%9F" class="d-flex play-button align-items-center" data-fancybox>
                <span class="icon mr-3">
                  <span class="icon-play"></span>
                </span>
                <div class="caption">
                  <h3 class="m-0">Sei un vero <br> egoista?</h3>
                </div>
              </a>
            </div>
          </div>
          <div class="item">
            <div class="video-media">
              <img src="images/immagine_video-kuroko.jpg" alt="Image" class="img-fluid">
              <a href="https://www.youtube.com/watch?v=CEHHkKVT1Mk&ab_channel=Tom" class="d-flex play-button align-items-center" data-fancybox>
                <span class="icon mr-3">
                  <span class="icon-play"></span>
                </span>
                <div class="caption">
                  <h3 class="m-0">Tu hai anche tutto il talento del mondo, ma hai anche l'ossessione?</h3>
                </div>
              </a>
            </div>
          </div>
          <div class="item">
            <div class="video-media">
              <img src="images/immagine_video-haikyuu.jpg" alt="Image" class="img-fluid">
              <a href="https://www.youtube.com/watch?v=WGMc7SxIZfU&ab_channel=NekoAMVs" class="d-flex play-button align-items-center" data-fancybox>
                <span class="icon mr-3">
                  <span class="icon-play"></span>
                </span>
                <div class="caption">
                  <h3 class="m-0">Vola in alto !</h3>
                </div>
              </a>
            </div>
          </div>

          <div class="item">
            <div class="video-media">
              <img src="images/immagine_video-isagi.jpg" alt="Image" class="img-fluid">
              <a href="https://www.youtube.com/watch?v=XhA9pM5_Y2E&ab_channel=AKUN%E5%BC%9F" class="d-flex play-button align-items-center" data-fancybox>
                <span class="icon mr-3">
                  <span class="icon-play"></span>
                </span>
                <div class="caption">
                  <h3 class="m-0">Sei un vero <br> egoista?</h3>
                </div>
              </a>
            </div>
          </div>
          <div class="item">
            <div class="video-media">
              <img src="images/immagine_video-kuroko.jpg" alt="Image" class="img-fluid">
              <a href="https://www.youtube.com/watch?v=CEHHkKVT1Mk&ab_channel=Tom" class="d-flex play-button align-items-center" data-fancybox>
                <span class="icon mr-3">
                  <span class="icon-play"></span>
                </span>
                <div class="caption">
                  <h3 class="m-0">Tu hai anche tutto il talento del mondo, ma hai anche l'ossessione?</h3>
                </div>
              </a>
            </div>
          </div>
          
          <div class="item">
            <div class="video-media">
              <img src="images/immagine_video-haikyuu.jpg" alt="Image" class="img-fluid">
              <a href="https://www.youtube.com/watch?v=WGMc7SxIZfU&ab_channel=NekoAMVs" class="d-flex play-button align-items-center" data-fancybox>
                <span class="icon mr-3">
                  <span class="icon-play"></span>
                </span>
                <div class="caption">
                  <h3 class="m-0">Vola in alto !</h3>
                </div>
              </a>
            </div>
          </div>

        </div>

      </div>
    </div>
    
    
    <!--Modal   -->
    <!-- Modal (Bootstrap 4) -->
<div
  class="modal fade"
  id="LearnMoreModal"
  tabindex="0"
  role="dialog"
  aria-labelledby="LearnMoreModalLabel"
  aria-hidden="true"
>
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content bg-secondary text-white border-0">
      <!-- Header con titolo e bottone di chiusura -->
      <div class="modal-header">
        <h5 class="modal-title text-uppercase" id="LearnMoreModalLabel">Learn More</h5>
        <button
          type="button"
          class="close"
          data-dismiss="modal"
          aria-label="Close"
        >
          <span aria-hidden="true">&times;</span>
        </button>
      </div>

      <!-- Body -->
      <div class="modal-body bg-dark text-white">
        <p class="item-intro text-muted btn byn-primary"> <h4> - Come unirti alla sfida: </h4> </p>
        
        <p>
          Per accedere a un torneo, è necessario che il presidente della squadra, crei un account e selezioni lo sport e il torneo per il quale iscriversi e creare la squadra, con l'aggiunta delle informazioni relative ai giocatori, rigorosamente 7. <br> Chi sarà il più forte?
        </p>
        
      </div>

      <!-- Footer con bottone di chiusura -->
      <div class="modal-footer">
        <button
          type="button"
          class="btn btn-primary text-uppercase"
          data-dismiss="modal"
        >
          <i class="fas fa-times mr-1"></i> Chiudi
        </button>
      </div>
    </div>
  </div>
</div>


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


  <script src="js/main.js"></script>

</body>

</html>