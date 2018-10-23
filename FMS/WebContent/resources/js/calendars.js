function loadingIcon(message) {
	return '&nbsp;&nbsp;&nbsp;&nbsp;<span class="fas fa-sync fa-2x fa-spin loadingIcon" style="color:#EF0719;"></span><span>&nbsp;&nbsp;&nbsp;' + message + '</span>'
}

function getInformation(roomCategory) {
    /*
    *  C - Conference (Large meetings)
	*  M - Meeting (Scrum, small meetings)
	*  T - Training
	*  L - Lab (POS lab, Pivotal lab)
	*  R - Recreation
    */
    switch (roomCategory) {
        case "C":
            return {icon: "comments", color: "#00bcd4"};
        case "M":
            return {icon: "bolt", color: "#673ab7"};
        case "T":
            return {icon: "graduation-cap", color: "#e91e63"};
        case "L":
            return {icon: "flask", color: "#ff9800"};
        case "R":
            return {icon: "chess-knight", color: "#009688"};
        case "X":
        	return {icon: "lock", color: "#db4437"};
    }
}

function featureIconify(name, qty) {
	if (qty === 0)
		return "";
	
	switch (name) {
    	case "CHAIR":
    		return '<i class="material-icons dp24">event_seat</i> x ' + qty + '<br />';
    	case "PROJECTOR":
    		return '<i class="material-icons dp24">videocam</i> x ' + qty + '<br />';
    	case "WHITEBOARD":
    		return '<i class="glyphicon glyphicon-blackboard" style="font-size:24px;"></i> x ' + qty + '<br />';
    	case "DESKTOP":
    		return '<i class="material-icons dp24">laptop</i> x ' + qty + '<br />';
    	case "TELEVISION":
    		return '<i class="material-icons dp24">tv</i> x ' + qty + '<br />';
	}
}

function parseMyJSON(features) {
	if (typeof(features) === "string")
		return JSON.parse(features.substring(0, features.length - 2)+ ']');
	
	return features;
}

function allFeatureIcons(features) {
	features = parseMyJSON(features);
	if (features.length === 0 || (features[0].quantity === 0 && features[1].quantity === 0 && features[2].quantity === 0 && features[3].quantity === 0 && features[4].quantity === 0))
		return "Nothing exists in this room<br />";
	var str = "";
	for (var i = 0; i < features.length; ++i) {
		str += featureIconify(features[i].name, features[i].quantity);
	}
	
	return str;
}

function iconify(icon, color, size) {
    return '<span class="fas fa-fw fa-' + size + ' fa-' + icon + '" style="color:' + color + ';margin-bottom:5px;"></span>&nbsp;&nbsp;';
}

function durationString(totalTime) {
    var hours = Math.floor(totalTime / 1000 / 60 / 60);
    var minutes = totalTime / 1000 / 60 % 60;
    var hoursMinutes = "";
    if (hours > 0) {
        hoursMinutes += hours + " hour";
        if (hours > 1)
            hoursMinutes += "s";
        hoursMinutes += " ";
    }
    if (minutes > 0)
        hoursMinutes += minutes + " minutes";

    return hoursMinutes;
}