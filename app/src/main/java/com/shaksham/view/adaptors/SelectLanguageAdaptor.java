package com.shaksham.view.adaptors;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shaksham.R;
import com.shaksham.model.PojoData.SelectedLanguagePojo;
import com.shaksham.utils.AppUtility;
import java.util.ArrayList;
import java.util.List;

public class SelectLanguageAdaptor extends RecyclerView.Adapter<SelectLanguageAdaptor.MySelectLanguageView> {
    private List<String> languageList;
    private Context context;
    private Dialog dialog;
    private TextView selectedLanguageTV;
    private List<SelectedLanguagePojo> selectedLanguages=new ArrayList<SelectedLanguagePojo>();

    public SelectLanguageAdaptor(Context context, List<String> languageList, Dialog dialog,TextView selectedLanguageTV ){
        this.languageList=languageList;
        this.context=context;
        this.dialog=dialog;
        this.selectedLanguageTV=selectedLanguageTV;
    }

    @NonNull
    @Override
    public MySelectLanguageView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.select_language_view_item,parent,false);
        return new MySelectLanguageView(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MySelectLanguageView holder, int position) {
            String language=languageList.get(position);
            AppUtility.getInstance().showLog("language="+language,SelectLanguageAdaptor.class);
            holder.selectLanguageItemViewTV.setText(language);

            if((holder.selectLanguageItemViewCB).isChecked()){
                holder.selectLanguageItemViewCB.setChecked(false);
            }

            holder.selectLanguageItemViewCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtility.getInstance().showLog("position"+position,SelectLanguageAdaptor.class);
                if(((CheckBox)v).isChecked()){

                    if(selectedLanguages.size()<=1){
                        SelectedLanguagePojo selectedLanguagePojo=new SelectedLanguagePojo();
                        selectedLanguagePojo.setSelectedLanguage(languageList.get(position));
                        selectedLanguagePojo.setLanguageCode("hi");
                        selectedLanguages.add(selectedLanguagePojo);
                        AppUtility.getInstance().showLog("selectedLanguagesListSize"+selectedLanguages.size(),SelectLanguageAdaptor.class);

                    } else {
                        Toast.makeText(context,R.string.toast_language_max_selection,Toast.LENGTH_SHORT).show();
                        selectedLanguages.clear();
                        refreshAdapter(languageList);
                    }
                }
            }
        });

        selectedLanguageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedLanguages.size()==0){
                    Toast.makeText(context, R.string.toast_language_min_selection, Toast.LENGTH_SHORT).show();
                }else {
                    for(int i=0;i<selectedLanguages.size();i++){
                        SelectedLanguagePojo selectedLanguagePojo=selectedLanguages.get(i);
                        AppUtility.getInstance().showLog("selectedLanguage"+selectedLanguagePojo.getSelectedLanguage()+","+"code="+selectedLanguagePojo.getLanguageCode(),SelectLanguageAdaptor.class);
                    }
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

 private void refreshAdapter(List< String > languageList){
        this.languageList = languageList;
        notifyDataSetChanged();
    }

  public class MySelectLanguageView extends RecyclerView.ViewHolder {
       public TextView selectLanguageItemViewTV;
       public CheckBox selectLanguageItemViewCB;

      public MySelectLanguageView(@NonNull View itemView) {
          super(itemView);

          selectLanguageItemViewTV=(TextView)itemView.findViewById(R.id.select_language_item_viewTV);
          selectLanguageItemViewCB=(CheckBox)itemView.findViewById(R.id.selected_language_item_viewCB);

      }
  }
}
