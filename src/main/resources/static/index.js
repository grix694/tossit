
import Vue from 'vue';
import {Tabs, Tab} from 'vue-tabs-component';

Vue.component('tabs', Tabs);
Vue.component('tab', Tab);

function drawCheckeredBackground(can, nRow, nCol) {
    var ctx = can.getContext("2d");
    var w = can.width;
    var h = can.height;

    nRow = nRow || 8;    // default number of rows
    nCol = nCol || 8;    // default number of columns

    w /= nCol;            // width of a block
    h /= nRow;            // height of a block

    for (var i = 0; i < nRow; ++i) {
        for (var j = 0, col = nCol / 2; j < col; ++j) {
            ctx.rect(2 * j * w + (i % 2 ? 0 : w), i * h, w, h);
        }
    }

    ctx.fill();
}

window.addEventListener('load', function () {
	//var canvas = document.getElementById("canvas");
	
	//drawCheckeredBackground(canvas);
})


/*
Vue.component('user-info', {
    props: ['thing'],
    template: 
    	`<div>
			<p>Username: {{thing.name}}</p>
		</div>`
});


window.addEventListener('load', function () {
	//drawCheckeredBackground(canvas);
//	var app = new Vue({
//		el: '#app',
//		data: {
//			title: 'Tossit is the Title!',
//			people: [
//				{id: 1, name: 'Gavin'},
//				{id: 2, name: 'Fop'}
//			]
//		} 
//	})
	
})

//*/
