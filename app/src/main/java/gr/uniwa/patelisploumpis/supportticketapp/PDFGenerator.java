package gr.uniwa.patelisploumpis.supportticketapp;

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

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator extends AsyncTask<ATaskParams, Integer, Void> {

    private static Bitmap bitmap;
    private static final int PDFPageHeight = 780;
    private static final int PDFPageWidth = 620;
    private static final File storageDir = new File(Environment.getExternalStorageDirectory() + "/SupportTickets");
    private SupportTicket ticket;

    @Override
    protected Void doInBackground(ATaskParams... params) {
        generatePDF(params[0].getAppContext(), params[0].getTicketID());
        return null;
    }

    private void generatePDF(Context context, int ticketID) {

        PdfDocument pdfDocument = new PdfDocument();
        Paint PDFPaint = new Paint();
        Paint PDFTitle = new Paint();

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ticket_186px);
        ticket = DatabaseHelper.getInstance(context).getTicketByID(ticketID);

        PdfDocument.PageInfo PDFTicketInfo = new PdfDocument.PageInfo.Builder(PDFPageWidth, PDFPageHeight, 1).create();
        PdfDocument.Page PDFTicketPage = pdfDocument.startPage(PDFTicketInfo);

        Canvas canvas = PDFTicketPage.getCanvas();
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 105, 105, false), 56, 40, PDFPaint);

        PDFTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        PDFTitle.setColor(ContextCompat.getColor(context, R.color.primaryDarkColor));

        PDFTitle.setTextSize(24);
        canvas.drawText("Support Ticket App", 220, 80, PDFTitle);

        PDFTitle.setTextSize(17);
        canvas.drawText("Easy Support Ticket Generation", 220, 110, PDFTitle);

        PDFTitle.setTextSize(15);
        PDFTitle.setTextAlign(Paint.Align.LEFT);
        PDFTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        PDFTitle.setColor(ContextCompat.getColor(context, R.color.black));

        canvas.drawText("Ticket ID:", 50, 250, PDFTitle);
        canvas.drawText("Technician's Name:", 50, 275, PDFTitle);
        canvas.drawText("Client's Name:", 50, 300, PDFTitle);
        canvas.drawText("Client's Address:", 50, 325, PDFTitle);
        canvas.drawText("Client's Phone:", 50, 350, PDFTitle);
        canvas.drawText("Client's Email:", 50, 375, PDFTitle);
        canvas.drawText("Date:", 50, 400, PDFTitle);
        canvas.drawText("Labor Type:", 50, 425, PDFTitle);
        canvas.drawText("Labor Hours:", 50, 450, PDFTitle);
        canvas.drawText("Labor Description:", 50, 475, PDFTitle);

        PDFTitle.setColor(ContextCompat.getColor(context, R.color.blue_gray_800));
        canvas.drawText(ticket.getTicketID(), 250, 250, PDFTitle);
        canvas.drawText(ticket.getTechnicianName(), 250, 275, PDFTitle);
        canvas.drawText(ticket.getClientName(), 250, 300, PDFTitle);
        canvas.drawText(ticket.getClientAddress(), 250, 325, PDFTitle);
        canvas.drawText(ticket.getClientPhone(), 250, 350, PDFTitle);
        canvas.drawText(ticket.getClientEmail(), 250, 375, PDFTitle);
        canvas.drawText(ticket.getLaborDate(), 250, 400, PDFTitle);
        canvas.drawText(ticket.getLaborType(), 250, 425, PDFTitle);
        canvas.drawText(String.valueOf(ticket.getLaborHours()), 250, 450, PDFTitle);
        canvas.drawText(ticket.getLaborDescription(), 250, 475, PDFTitle);

        PDFTitle.setTextAlign(Paint.Align.CENTER);
        PDFTitle.setColor(ContextCompat.getColor(context, R.color.black));
        canvas.drawText("© 2022 Patelis - Ploumpis", 320, 740, PDFTitle);

        pdfDocument.finishPage(PDFTicketPage);

        if(!storageDir.exists()) {
            storageDir.mkdir();
        }

        File pdfFile = new File(storageDir.toString(), "ticket#" + ticket.getTicketID() + ".pdf");
        System.out.println(storageDir);
        System.out.println(pdfFile);

        try{
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
            Log.i("INFO","PDF File Generated Successfully");
        }catch(IOException e) {
            e.printStackTrace();
            Log.i("INFO","Error Generating PDF File");
        }

        pdfDocument.close();
    }

}
