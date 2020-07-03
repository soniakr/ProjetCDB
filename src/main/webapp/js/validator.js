function validateForm() {
	var result = true;
	var introduced = document.getElementById("introduced").value;
	var discontinued = document.getElementById("discontinued").value;
	
	if (introduced != "") {
		introduced = new Date(introduced);
		if (isADate(introduced)){
		 	if (discontinued != "") {
				discontinued = new Date(discontinued);
				if (isADate(discontinued)){
					if (discontinued<introduced){
						result = false;
						alert("The discontinued date must be before introduced date.");
					}
				} else {
					result = false;
					alert("The discontinued date format is invalid");
				}
			}
		} else {
			result = false;
			alert("The introduced date format is invalid");
		}
	} else if (discontinued != "") {
		result = false;
		alert("Impossible to set a discontinued date if the introduced date is not defined.");
	}
	return result;
}

function isADate(date) {
	return date instanceof Date && !isNaN(date.getTime())
}