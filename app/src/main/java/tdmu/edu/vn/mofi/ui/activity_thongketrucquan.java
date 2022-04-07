package tdmu.edu.vn.mofi.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tdmu.edu.vn.mofi.R;
import tdmu.edu.vn.mofi.SQL.DatabaseHelper;
import tdmu.edu.vn.mofi.modals.Giaodich;

public class activity_thongketrucquan extends Activity {
    private PieChart mChart, tChart;
    private CombinedChart thuChart, chiChart;
    String user;
    DatabaseHelper db;
    private ArrayList<Giaodich> sumthu, sumchi, sumthuthang, sumchithang, sthuthang, schithang;
    String ngaygd;
    int sotienthu, sotienchi, thuthang, chithang;
    private int mYear,mMonth,Year;
    String tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke_trucquan);
        db=new DatabaseHelper(getApplicationContext());
        laydulieu();
        setDataNgay();

        chartNgay();
        addDataSet(mChart, sotienthu, sotienchi);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getApplicationContext(), "Value: "
                        + e.getY()
                        + ", index: "
                        + h.getX()
                        + ", DataSet index: "
                        + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chartThang();
        setDataThang();
        addDataSetThang(tChart, thuthang, chithang);
        chartThu();
        chartChi();
    }
    public void laydulieu(){
        Intent intent = getIntent();
        user = intent.getStringExtra("email");
    }

    private  void chartNgay(){
        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
//        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Thống kê theo ngày");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        mChart.getDescription().setEnabled(false);
    }
    private  void chartThang(){
        tChart = (PieChart) findViewById(R.id.piechartthangnay);
        tChart.setRotationEnabled(true);
        tChart.setHoleRadius(35f);
        tChart.setTransparentCircleAlpha(0);
        tChart.setCenterText("Thống kê theo tháng");
        tChart.setCenterTextSize(10);
        tChart.setDrawEntryLabels(true);
        tChart.getDescription().setEnabled(false);
    }
    private  void chartThu(){
        thuChart = (CombinedChart) findViewById(R.id.combinedChart);
        thuChart.getDescription().setEnabled(false);
        thuChart.setBackgroundColor(Color.WHITE);
        thuChart.setDrawGridBackground(false);
        thuChart.setDrawBarShadow(false);
        thuChart.setHighlightFullBarEnabled(false);

        YAxis rightAxis = thuChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = thuChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("T1");
        xLabel.add("T2");
        xLabel.add("T3");
        xLabel.add("T4");
        xLabel.add("T5");
        xLabel.add("T6");
        xLabel.add("T7");
        xLabel.add("T8");
        xLabel.add("T9");
        xLabel.add("T10");
        xLabel.add("T11");
        xLabel.add("T12");

        XAxis xAxis = thuChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        thuChart.setData(data);
        thuChart.invalidate();
    }
    private  void chartChi(){
        chiChart = (CombinedChart) findViewById(R.id.Chartchi);
        chiChart.getDescription().setEnabled(false);
        chiChart.setBackgroundColor(Color.WHITE);
        chiChart.setDrawGridBackground(false);
        chiChart.setDrawBarShadow(false);
        chiChart.setHighlightFullBarEnabled(false);

        YAxis rightAxis = chiChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = chiChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("T1");
        xLabel.add("T2");
        xLabel.add("T3");
        xLabel.add("T4");
        xLabel.add("T5");
        xLabel.add("T6");
        xLabel.add("T7");
        xLabel.add("T8");
        xLabel.add("T9");
        xLabel.add("T10");
        xLabel.add("T11");
        xLabel.add("T12");

        XAxis xAxis = chiChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart2());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        chiChart.setData(data);
        chiChart.invalidate();
    }
    private static void addDataSet(PieChart pieChart, int thu, int chi) {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new PieEntry(thu, "Tổng thu"));
        yEntrys.add(new PieEntry(chi, "Tổng chi"));
        ArrayList<String> xEntrys = new ArrayList<>();
//        float[] yData = { thu, chi };
        String[] xData = { "Tổng thu", "Tổng chi" };

//        for (int i = 0; i < yData.length;i++){
//            yEntrys.add(new PieEntry(yData[i],i));
//        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Tổng tiền");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    private static void addDataSetThang(PieChart pieChart, int thu, int chi) {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new PieEntry(thu, "Tổng thu"));
        yEntrys.add(new PieEntry(chi, "Tổng chi"));
        ArrayList<String> xEntrys = new ArrayList<>();
//        float[] yData = { thu, chi };
        String[] xData = { "Tổng thu", "Tổng chi" };

//        for (int i = 0; i < yData.length;i++){
//            yEntrys.add(new PieEntry(yData[i],i));
//        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Tổng tiền");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    public void setDataNgay(){
        // set up calendar
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ngaygd=sdf.format(c.getTime());

        sumchi= db.SumThongketheongay("CHI",user,ngaygd);
        if (sumchi.get(0).getSotien() == null){
            sotienchi = 0;
        }
        else{
            sotienchi=Integer.parseInt(sumchi.get(0).getSotien());
            sotienchi=Math.abs(sotienchi);
        }

        sumthu= db.SumThongketheongay("THU",user,ngaygd);
        if (sumthu.get(0).getSotien() == null){
            sotienthu = 0;
        }
        else{sotienthu=Integer.parseInt(sumthu.get(0).getSotien());}
    }
    private void setDataThang(){
        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mMonth=mMonth+1;
        sumchithang= db.SumThongketheothang("CHI",user,mMonth+"", mYear+"");
        if (sumchithang.get(0).getSotien() == null){
            chithang=0;
        }
        else{
            chithang=Integer.parseInt(sumchithang.get(0).getSotien());
            chithang= Math.abs(chithang);
        }
        sumthuthang= db.SumThongketheothang("THU",user,mMonth+"", mYear+"");
        if (sumthuthang.get(0).getSotien() == null){
            thuthang=0;
        }
        else{
            thuthang=Integer.parseInt(sumthuthang.get(0).getSotien());

        }
    }

    private DataSet dataChart() {

        Calendar c=Calendar.getInstance();
        Year=c.get(Calendar.YEAR);

        LineData d = new LineData();
        int[] data = new int[12] ;
        for (int i = 0; i < 12; i++) {
            if (i<10){
                tmp=i+1+"";
                tmp="0"+tmp;
            } else {
                tmp= i+1+"";
            }
            sthuthang= db.SumThongketheothang("THU",user,tmp, Year+"");
            if (sthuthang.get(0).getSotien() == null){
                data[i]=0;
            }
            else{
                data[i]=Integer.parseInt(sthuthang.get(0).getSotien());

            }
        }
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Tổng thu từng tháng");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

    private DataSet dataChart2() {

        Calendar c=Calendar.getInstance();
        Year=c.get(Calendar.YEAR);

        LineData d = new LineData();
        int[] data = new int[12] ;
        for (int i = 0; i < 12; i++) {
            if (i<10){
                tmp=i+1+"";
                tmp="0"+tmp;
            } else {
                tmp= i+1+"";
            }
            schithang= db.SumThongketheothang("CHI",user,tmp, Year+"");
            if (schithang.get(0).getSotien() == null){
                data[i]=0;
            }
            else{
                data[i]=Integer.parseInt(schithang.get(0).getSotien());
                data[i]= Math.abs(data[i]);
            }
        }
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Tổng chi từng tháng");
        set.setColor(Color.RED);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.RED);
        set.setCircleRadius(5f);
        set.setFillColor(Color.RED);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.RED);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }
}
