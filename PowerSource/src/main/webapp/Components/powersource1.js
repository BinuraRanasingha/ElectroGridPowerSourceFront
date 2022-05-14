$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePowerSourceForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidPowerIDSubmit").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PowerSourceAPI",
		type : type,
		data : $("#formPower").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPowerSaveComplete(response.responseText, status);
		}
	});
});

function onPowerSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divPowerSourceGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidPowerIDSubmit").val("");
	$("#formPower")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidPowerIDSubmit").val(
					$(this).closest("tr").find('#hidPowerSourceIDUpdate').val());
			$("#pName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#address").val($(this).closest("tr").find('td:eq(1)').text());
			$("#province").val($(this).closest("tr").find('td:eq(2)').text());
			$("#powerSourceType").val($(this).closest("tr").find('td:eq(3)').text());
			$("#power").val($(this).closest("tr").find('td:eq(4)').text());
			$("#mDay").val($(this).closest("tr").find('td:eq(5)').text());
			$("#engID").val($(this).closest("tr").find('td:eq(6)').text());
		});

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PowerSourceAPI",
		type : "DELETE",
		data : "ID=" + $(this).data("powersource"),
		dataType : "text",
		complete : function(response, status) {
			onPowerDeleteComplete(response.responseText, status);
		}
	});
});

function onPowerDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divPowerSourceGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL=========================================================================
// CLIENT-MODEL================================================================
function validatePowerSourceForm()
{
	// POWERSOURCE NAME
	if ($("#pName").val().trim() == "")
	{
		return "Insert power source name.";
	}
	// POWERSOURCE ADDRESS
	if ($("#address").val().trim() == "")
	{
		return "Insert the power source address.";
	}
	
	// PROVINCE-------------------------------
	if ($("#province").val().trim() == "")
	{
		return "Insert the province.";
	}
	
	// POWERSOURCE TYPE-------------------------------
	if ($("#powerSourceType").val().trim() == "")
	{
		return "Select the power source type.";
	}
	
	//POWER GENERATED
	if ($("#power").val().trim() == "")
	{
		return "Insert power generated.";
	}	
	
	// is numerical value
	var tmpPower = $("#power").val().trim();
	if (!$.isNumeric(tmpPower))
	{
		return "Insert a numerical value for power generated.";
	}
	
	
	//EmployeeID
	if ($("#engID").val().trim() == "")
	{
		return "Insert the employee id.";
	}
	
	//is a numerical value
	var tmpEmp = $("#engID").val().trim();
	if(!$.isNumeric(tmpEmp))
	{
		return "Insert a numerical value for EmployeeID."
	}
	return true;
}