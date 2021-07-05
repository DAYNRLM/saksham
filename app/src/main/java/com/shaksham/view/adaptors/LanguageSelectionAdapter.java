package com.shaksham.view.adaptors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.PojoData.LanguagePojo;
import com.shaksham.presenter.Activities.HomeActivity;
import com.shaksham.utils.AppUtility;
import com.shaksham.utils.DialogFactory;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import java.util.List;
import java.util.Locale;

public class LanguageSelectionAdapter extends RecyclerView.Adapter<LanguageSelectionAdapter.MyViewHolder> {
    Context context;
    List<LanguagePojo> languageDataList;
    Locale myLocale;
    String currentLanguage = "en", currentLang;

    public LanguageSelectionAdapter(Context context, List<LanguagePojo> languageDataList) {
        this.context = context;
        this.languageDataList = languageDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLanguageView= LayoutInflater.from(parent.getContext()).inflate(R.layout.language_selection_custom_layout,parent,false);
        return new LanguageSelectionAdapter.MyViewHolder(myLanguageView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.localLanguage.setText(languageDataList.get(position).getLocalLanguage());
        holder.englishLanguage.setText(languageDataList.get(position).getEnglishLanguage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFactory.getInstance().showAlertDialog(context, R.drawable.ic_launcher_background, "hiiii", "change language", "yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String languageCode = languageDataList.get(position).getLanguagecode();
                        String languageID = languageDataList.get(position).getLanguageid();
                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefKeyLanguageCode(),languageCode,context);
                        PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrfKeyLanguageId(),languageID,context);
                        AppUtility.getInstance().showLog("language code is :-" +languageCode,LanguageSelectionAdapter.class);
                        AppUtility.getInstance().setLocale(languageCode,context.getResources(),context);
                        Intent refresh = new Intent(context, HomeActivity.class);
                       context.startActivity(refresh);
                        AppUtility.getInstance().makeIntent(context,HomeActivity.class,true);
                    }
                }, "no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return languageDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView localLanguage,englishLanguage;
        ImageView okImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            englishLanguage = (TextView)itemView.findViewById(R.id.english_languageTv);
            localLanguage = (TextView)itemView.findViewById(R.id.Local_LanguageTv);
            okImage = (ImageView)itemView.findViewById(R.id.chklanguage);
        }
    }

    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(context, HomeActivity.class);
            refresh.putExtra(currentLang, localeName);
            context.startActivity(refresh);
        } else {
            Toast.makeText(context, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
