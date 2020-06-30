function validateForm() {
  var name = document.addComputer.computerName.value;
  var introduced = document.addComputer.introduced.value;
  var discontinued = document.addComputer.discontinued.value;
  
  if (name == "") {
    alert("Name must be filled out");
    return false;
  }
  
  if(introduced != "" & discontinued != ""){
	 introducedDate= new Date(introduced);
	 discontinuedDate= new Date(discontinued);
	  if(discontinuedDate<introducedDate){
		  alert("Introduced date must be before discontinued date");
		    return false;
	  }
  }
  
} 