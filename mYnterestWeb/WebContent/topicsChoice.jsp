
<html>

<head>
		<title>topics</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		
		<link rel="stylesheet" href="resources/assets/css/main.css" />
		
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
	</head>

<body>

<%=request.getAttribute("email")%> 



	<section>
		<h4>topics</h4>
		
		<form action="ServletTopicsChoice" method="post">
		<fieldset>
		
		<% String hidden = (String) request.getAttribute("email"); %>
	
		<input type="hidden" name="email" value=<%=hidden %>>
	
				<div class="6u 12u$(small)">
					<input type="checkbox" id="idsport" name="sport"> 
					<label for="idsport">Sport</label>
				</div>
				<div class="6u$ 12u$(small)">
					<input type="checkbox" id="idcronaca" name="cronaca">
					<label for="idcronaca">Cronaca</label>
				</div>
				
				<div class="6u 12u$(small)">
					<input type="checkbox" id="idpolitica" name="politica"> <label
						for="idpolitica">Politica</label>
				</div>
				<div class="6u$ 12u$(small)">
					<input type="checkbox" id="idtecnologia" name="tecnologia">
					<label for="idtecnologia">Tecnologia</label>
				</div>
				
					<div class="6u$ 12u$(small)">
					<input type="checkbox" id="idscienze" name="scienze">
					<label for="idscienze">Scienze</label>
				</div>
				
					<div class="6u$ 12u$(small)">
					<input type="checkbox" id="ideconomia" name="economia">
					<label for="ideconomia">Economia</label>
				</div>
				
					<div class="6u$ 12u$(small)">
					<input type="checkbox" id="idesteri" name="esteri">
					<label for="idesteri">Esteri</label>
				</div>
				
				</fieldset>
				
				<br>
				
				
				<input type="submit" value="OK" class="button special" />
				
				</form>
				
		
	</section>

	

</body>
</html>