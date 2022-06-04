package gr.uniwa.patelisploumpis.supportticketapp.Task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.ASyncTaskParams;
import gr.uniwa.patelisploumpis.supportticketapp.Model.SupportTicket;
import gr.uniwa.patelisploumpis.supportticketapp.R;

public class PDFGenerator extends AsyncTask<ASyncTaskParams, Void, Boolean> {

    private static Bitmap bitmap;
    private  Context mContext;
    private static final int PDFPageHeight = 780;
    private static final int PDFPageWidth = 620;
    private static final File storageDir = new File(Environment.getExternalStorageDirectory() + "/SupportTickets");
    private SupportTicket supportTicket;

    @Override
    protected Boolean doInBackground(ASyncTaskParams... params) {
        mContext = params[0].getAppContext();
        return generatePDF(params[0].getAppContext(), params[0].getTicketID());
    }

    @Override
    protected void onPostExecute(Boolean successFlag) {
        if(successFlag){
            Toast.makeText(mContext, "PDF Generated Successfully", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(mContext, "PDF Generation Failed", Toast.LENGTH_LONG).show();
        }
    }

    private boolean generatePDF(Context context, String ticketID) {

        boolean successFlag;
        PdfDocument pdfDocument = new PdfDocument();
        Paint PDFPaint = new Paint();
        Paint PDFTitle = new Paint();

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ticket_186px);
        supportTicket = DatabaseHelper.getInstance(context).getTicketByID(ticketID);

        PdfDocument.PageInfo PDFTicketInfo = new PdfDocument.PageInfo.Builder(PDFPageWidth, PDFPageHeight, 1).create();
        PdfDocument.Page PDFTicketPage = pdfDocument.startPage(PDFTicketInfo);

        Canvas canvas = PDFTicketPage.getCanvas();
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 105, 105, false), 56, 40, PDFPaint);

        PDFTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        PDFTitle.setColor(ContextCompat.getColor(context, R.color.primaryDarkColor));

        PDFTitle.setTextSize(24);
        canvas.drawText(context.getResources().getString(R.string.app_name), 220, 80, PDFTitle);

        PDFTitle.setTextSize(17);
        canvas.drawText("Easy Support Ticket Generation", 220, 110, PDFTitle);

        PDFTitle.setTextSize(15);
        PDFTitle.setTextAlign(Paint.Align.LEFT);
        PDFTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        PDFTitle.setColor(ContextCompat.getColor(context, R.color.black));

        canvas.drawText(context.getResources().getString(R.string.hint_ticket_id) + ":", 50, 250, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_technician_name) + ":", 50, 275, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_client_name) + ":", 50, 300, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_client_address) + ":", 50, 325, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_client_phone) + ":", 50, 350, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_client_email) + ":", 50, 375, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_labor_date) + ":", 50, 400, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_labor_type) + ":", 50, 425, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_labor_hours) + ":", 50, 450, PDFTitle);
        canvas.drawText(context.getResources().getString(R.string.hint_labor_description) + ":", 50, 475, PDFTitle);

        PDFTitle.setColor(ContextCompat.getColor(context, R.color.blue_gray_800));
        canvas.drawText(supportTicket.getTicketID(), 250, 250, PDFTitle);
        canvas.drawText(supportTicket.getTechnicianName(), 250, 275, PDFTitle);
        canvas.drawText(supportTicket.getClientName(), 250, 300, PDFTitle);
        canvas.drawText(supportTicket.getClientAddress(), 250, 325, PDFTitle);
        canvas.drawText(supportTicket.getClientPhone(), 250, 350, PDFTitle);
        canvas.drawText(supportTicket.getClientEmail(), 250, 375, PDFTitle);
        canvas.drawText(supportTicket.getLaborDate(), 250, 400, PDFTitle);
        canvas.drawText(supportTicket.getLaborType(), 250, 425, PDFTitle);
        canvas.drawText(String.valueOf(supportTicket.getLaborHours()), 250, 450, PDFTitle);
        canvas.drawText(supportTicket.getLaborDescription(), 250, 475, PDFTitle);

        PDFTitle.setTextAlign(Paint.Align.CENTER);
        PDFTitle.setColor(ContextCompat.getColor(context, R.color.black));
        canvas.drawText(context.getResources().getString(R.string.app_info), 320, 740, PDFTitle);

        pdfDocument.finishPage(PDFTicketPage);

        if(!storageDir.exists()) {
            storageDir.mkdir();
        }

        File pdfFile = new File(storageDir.toString(), "ticket#" + supportTicket.getTicketID() + ".pdf");

        try{
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
            Log.i("INFO","PDF File Generated Successfully");
            successFlag = true;
        }catch(IOException e) {
            successFlag = false;
            e.printStackTrace();
            Log.i("INFO","Error Generating PDF File");
        }

        pdfDocument.close();
        return successFlag;
    }

}
