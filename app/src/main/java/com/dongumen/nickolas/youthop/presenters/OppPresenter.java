package com.dongumen.nickolas.youthop.presenters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.activities.OppActivity;
import com.dongumen.nickolas.youthop.models.enteties.Opportunity;
import com.dongumen.nickolas.youthop.models.remote.OppDataSource;
import com.dongumen.nickolas.youthop.utils.DateUtil;
import com.dongumen.nickolas.youthop.view.OppView;
import com.google.firebase.database.Query;

import durdinapps.rxfirebase2.RxFirebaseDatabase;


@InjectViewState
public class OppPresenter extends MvpPresenter<OppView> {

    OppDataSource dataSource = new OppDataSource();

    public void loadOpp(String id) {
        Query query = dataSource.getmDatabase().child(id);
        RxFirebaseDatabase.observeSingleValueEvent(query, Opportunity.class)
                .subscribe(opportunity -> getViewState().showOpp(opportunity));
    }

    public void share(Opportunity opportunity, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getMessage(opportunity));
        Intent.createChooser(intent, "Share via");
        context.startActivity(intent);
    }

    private String getMessage(Opportunity opportunity) {
        return "YouthShapers \n" + opportunity.name + "\nDeadline: "
                + DateUtil.getDateFrormated(opportunity.deadline) + "\nTime Left: " +
                DateUtil.getDeadlineDays(opportunity.deadline) + "\nRegion: " +
                opportunity.place;
    }

    public void shareTwitter(Opportunity opportunity, Context context) {
        String url = "http://www.twitter.com/intent/tweet?text=" + getMessage(opportunity);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public void shareViber(Opportunity opportunity, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.viber.voip");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getMessage(opportunity));
        saveStartIntent(context, intent, "No Viber...");
    }

    public void shareWhatsapp(Opportunity opportunity, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getMessage(opportunity));
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        saveStartIntent(context, intent, "No Whatsapp...");
    }

    public void shareMail(Opportunity opportunity, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, opportunity.name);
        intent.putExtra(Intent.EXTRA_TEXT, getMessage(opportunity));
        saveStartIntent(context, intent, "No mail app...", "Send mail...");
    }

    private void saveStartIntent(Context context, Intent intent, String errorMessage) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            getViewState().showToast(errorMessage);
        }
    }

    private void saveStartIntent(Context context, Intent intent, String errorMessage, String title) {
        try {
            context.startActivity(Intent.createChooser(intent, title));
        } catch (ActivityNotFoundException ex) {
            getViewState().showToast(errorMessage);
        }
    }

    public void shareCalendar(Opportunity opportunity, Context context) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, opportunity.name);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                opportunity.dates.start);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                opportunity.dates.end);
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
        intent.putExtra(CalendarContract.Events.DESCRIPTION, opportunity.type);
        context.startActivity(intent);
    }

    public void applyNow(Opportunity opportunity, OppActivity oppActivity) {
        String url = opportunity.oppUrls.applyNowUrl;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        oppActivity.startActivity(i);
    }

    public void officialLink(Opportunity opportunity, OppActivity oppActivity) {
        String url = opportunity.oppUrls.linkUrl;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        oppActivity.startActivity(i);
    }
}
