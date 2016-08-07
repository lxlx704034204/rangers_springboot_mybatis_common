require.config({
	 paths:{
				"jquery" : ["http://libs.baidu.com/jquery/2.0.3/jquery","js/jquery"],
				"a" :"/js/a"  ,
				echarts : "/js/dist" 
	 },

	shim: {
	    "underscore" : {
	        exports : "_"
	    },
	    "jquery.form" : ["jquery"]
	}
})

