import Vue from 'vue'
//import cpt_input from '@/components/DataV/element/cpt-input'
import cpt_text from '@/components/DataV/element/cpt-text'
import cpt_carousel from '@/components/DataV/element/cpt-carousel'
import cpt_button from '@/components/DataV/element/cpt-button'
import cpt_image from '@/components/DataV/element/cpt-image'
import cpt_chart_column from '@/components/DataV/echarts/cpt-chart-column'
import cpt_chart_pie from '@/components/DataV/echarts/cpt-chart-pie'
import cpt_chart_clock from '@/components/DataV/echarts/cpt-chart-clock'
import cpt_dataV_border from '@/components/DataV/dataV/cpt-dataV-border'
import cpt_dataV_scrollTable from '@/components/DataV/dataV/cpt-dataV-scrollTable'
import cpt_dataV_scrollList from '@/components/DataV/dataV/cpt-dataV-scrollList'
import cpt_chart_mapGc from '@/components/DataV/echarts/cpt-chart-mapGc'
import cpt_chart_mapMigrate from '@/components/DataV/echarts/cpt-chart-mapMigrate'
import cpt_dataV_waterLevel from '@/components/DataV/dataV/cpt-dataV-waterLevel'
import cpt_dataV_decoration from '@/components/DataV/dataV/cpt-dataV-decoration'
import cpt_chart_line from '@/components/DataV/echarts/cpt-chart-line'
import cpt_dataV_digitalFlop from '@/components/DataV/dataV/cpt-dataV-digitalFlop'
import cpt_dataV_percentPond from '@/components/DataV/dataV/cpt-dataV-percentPond'
import cpt_iframe from '@/components/DataV/element/cpt-iframe'
import cpt_chart_tdColumn from '@/components/DataV/echarts/cpt-chart-tdColumn'
import cpt_dataV_activeRing from '@/components/DataV/dataV/cpt-dataV-activeRing'
import cpt_chart_gauge from '@/components/DataV/echarts/cpt-chart-gauge'


let cptList = [
    cpt_dataV_border,cpt_text,cpt_carousel,cpt_button,cpt_image,
    cpt_chart_column,cpt_chart_tdColumn,cpt_chart_pie,cpt_chart_clock,cpt_dataV_scrollTable,
    cpt_dataV_scrollList,cpt_chart_mapGc,cpt_chart_mapMigrate,
    cpt_dataV_waterLevel,cpt_dataV_decoration,cpt_chart_line,
    cpt_dataV_digitalFlop,cpt_dataV_percentPond,cpt_iframe,
    cpt_dataV_activeRing,cpt_chart_gauge
]

let cptGroups = {}
cptList.forEach(ele => {
    Vue.component(ele.name, ele);
    const title = ele.title ? ele.title:'未命名组件';
    const initWidth = ele.initWidth ? ele.initWidth:500;
    const initHeight = ele.initHeight ? ele.initHeight:400;
    const group = ele.group ? ele.group:'default';
    cptGroups[group] = cptGroups[group] ? cptGroups[group]:[]
    cptGroups[group].push({group:group,title: title,name:ele.name,icon:ele.icon,initWidth:initWidth,initHeight:initHeight});
});
export default cptGroups;
