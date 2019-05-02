Highcharts.setOptions({global: { useUTC: false } });
function parseJSON(msg) {
	return JSON.parse(msg.data.replace(/(NaN|Infinity)/ig, null));
}

function makePlot(element, name, prefix, isMultibeam, seriesType, xType) {
	$(element).highcharts({
		chart: {
			animation: false
		},
		xAxis: {
			type: xType||'datetime'
		},
		title: {
			text: name,
		},
		series: (isMultibeam ? [{ name: prefix+'B1H', type: seriesType }, { name: prefix+'B1V', type: seriesType }, { name: prefix+'B2H', type: seriesType }, { name: prefix+'B2V', type: seriesType }] : [ {name : prefix, type: seriesType } ])
	});
	return $(element).highcharts();
}
function evalFit(fitParams, xMin, xMax) {
	let NUMPOINTS = 200;
	if (xMax <= xMin) {
		return [];
	}
	var points = new Array(NUMPOINTS);
	for(var i=0; i<NUMPOINTS; i++) {
		let x = xMin + i*(xMax-xMin)/NUMPOINTS;
		points[i] = [x, fitParams.AMPLITUDE * Math.cos(x*fitParams.ANGULAR_FREQUENCY + fitParams.PHASE) + fitParams.OFFSET ];
	}
	return points;
}

var tunePlot = makePlot('#tunePlot', 'Tunes', 'tune', true, 'line');
var wsTune = new WebSocket("ws://"+location.host+"/acquiredTune");
wsTune.onmessage = (msg) => {
    $('#testTune').text(msg.data);
    var parsedData = parseJSON(msg);
    $(tunePlot.series).each((_, series) => series.addPoint([Date.now(), parsedData[series.name]], true, series.data.length > 500));
}

var chromaPlot = makePlot('#chromaPlot', 'Chroma', 'chroma', true, 'line');
var wsChroma = new WebSocket("ws://"+location.host+"/chroma/RATIO_OF_FIT_AMPLITUDES");
wsChroma.onmessage = (msg) => {
    $('#testChroma').text(msg.data);
    var parsedData = parseJSON(msg);
    $(chromaPlot.series).each((_, series) => series.addPoint([Date.now(), parsedData[series.name]], true, series.data.length > 500));
}

var tuneFitPlot = makePlot('#tuneFitPlot', 'Tune Fit Buffer', 'tune', true, 'scatter');
var wsTuneBuffer = new WebSocket("ws://"+location.host+"/tuneBuffer");
wsTuneBuffer.onmessage = (msg) => {
    var parsedData = parseJSON(msg);
    var times = parsedData.timeStamp;
    $(tuneFitPlot.series).each((_, series) => {
    	if (series.name in parsedData) {
    		series.setData(times.map((e,i) => [e, parsedData[series.name][i]]));
    	}
    });
}
var tuneFitSeries = {
	B1H: tuneFitPlot.addSeries({ name: 'fitB1H' }),
	B1V: tuneFitPlot.addSeries({ name: 'fitB1V' }),
	B2H: tuneFitPlot.addSeries({ name: 'fitB2H' }),
	B2V: tuneFitPlot.addSeries({ name: 'fitB2V' })
};
var wsTuneFit = new WebSocket("ws://"+location.host+"/tuneFit");
wsTuneFit.onmessage = (msg) => {
	$('#testTuneFit').text(msg.data)
    var parsedData = parseJSON(msg);
    var xMin = Math.min(...tuneFitPlot.series[0].xData)
    var xMax = Math.max(...tuneFitPlot.series[0].xData)
    for (var plane in tuneFitSeries) {
	    tuneFitSeries[plane].setData(evalFit(parsedData[plane], xMin, xMax));
    }
}

var momentumOffsetFitPlot = makePlot('#momentumOffsetFitPlot', 'deltap/p Fit Buffer', 'momentumOffset', false, 'scatter');
var wsMomentumOffsetBuffer = new WebSocket("ws://"+location.host+"/momentumOffsetBuffer");
wsMomentumOffsetBuffer.onmessage = (msg) => {
    var parsedData = parseJSON(msg);
    var times = parsedData.timeStamp;
    $(momentumOffsetFitPlot.series).each((_, series) => {
    	if (series.name in parsedData) {
    		series.setData(times.map((e,i) => [e, parsedData[series.name][i]]));
    	}
    });
}
var momentumFitSeries = momentumOffsetFitPlot.addSeries({ name: 'fit' });
var wsMomentumOffsetFit = new WebSocket("ws://"+location.host+"/momentumOffsetFit");
wsMomentumOffsetFit.onmessage = (msg) => {
	$('#testMomentumOffsetFit').text(msg.data)
    var parsedData = parseJSON(msg);
    var xMin = Math.min(...momentumOffsetFitPlot.series[0].xData)
    var xMax = Math.max(...momentumOffsetFitPlot.series[0].xData)
    momentumFitSeries.setData(evalFit(parsedData, xMin, xMax));
}

var tuneVsDeltapPlot = makePlot('#tuneVsDeltap', 'Tune vs Deltap/p Buffer', 'tune', true, 'scatter', 'double');
var wsTuneVsDeltap = new WebSocket("ws://"+location.host+"/tuneVsDeltap");
wsTuneVsDeltap.onmessage = (msg) => {
    var parsedData = parseJSON(msg);
    var deltapOverP = parsedData.deltapOverP;
    $(tuneVsDeltapPlot.series).each((_, series) => {
    	if (series.name in parsedData) {
    		series.setData(deltapOverP.map((e,i) => [e, parsedData[series.name][i]]));
    	}
    });
}

var tuneVsDeltapFitSeries = {
		B1H: tuneVsDeltapPlot.addSeries({ name: 'fitB1H' }),
		B1V: tuneVsDeltapPlot.addSeries({ name: 'fitB1V' }),
		B2H: tuneVsDeltapPlot.addSeries({ name: 'fitB2H' }),
		B2V: tuneVsDeltapPlot.addSeries({ name: 'fitB2V' })
	};
	
	
var wsTuneVsDeltapFit = new WebSocket("ws://"+location.host+"/tuneVsDeltapLinearFit");
wsTuneVsDeltapFit.onmessage = (msg) => {
	$('#testTuneVsDeltapLinearFit').text(msg.data);
	var parsedData = parseJSON(msg);
    var xMin = Math.min(...tuneVsDeltapPlot.series[0].xData)
    var xMax = Math.max(...tuneVsDeltapPlot.series[0].xData)
    for (var plane in tuneVsDeltapFitSeries) {
    	let slope = parsedData['linearTuneVsDeltap'+plane+'_slope']
    	let intersept = parsedData['linearTuneVsDeltap'+plane+'_intercept']
    	var points = new Array(2);
    	points[0] = [xMin, xMin * slope + intersept ];
    	points[1] = [xMax, xMax * slope + intersept ];
    	
    	tuneVsDeltapFitSeries[plane].setData(points);
    }
}

var wsServerModulationState = new WebSocket("ws://"+location.host+"/properties/ws/modulation-state");
wsServerModulationState.onmessage = (msg) => {
    $('.server-modulation-state').text('Sever modulation state: ' + ((msg.data=='true') ? 'ON' : 'OFF'))
}

$.ajaxSetup({
	headers: {
		"X-RBAC-TOKEN" : btoa("backdoor-for-demo-mode")
	}
});
 
$('.modulation').click(event => {
    var newState = event.target.value;
    console.log(newState);
    $.post('/properties/modulation-state', 'value='+newState);
});

$('.trim .apply').click(event => {
	let form = $(event.target).parent();
    $.post('/trim/' + form.data('trim-target'),
    	'trimB1H='+$('.trimB1H', form).val()+'&'+
    	'trimB1V='+$('.trimB1V', form).val()+'&'+
    	'trimB2H='+$('.trimB2H', form).val()+'&'+
    	'trimB2V='+$('.trimB2V', form).val());
    $('input', form).val(0.0);
});