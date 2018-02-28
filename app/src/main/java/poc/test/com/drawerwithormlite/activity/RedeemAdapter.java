package poc.test.com.drawerwithormlite.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kalamansee.R;
import com.kalamansee.customcomponents.SwipeRevealLayout;
import com.kalamansee.customcomponents.ViewBinderHelper;
import com.kalamansee.model.MyCampaignList;
import com.kalamansee.utils.ConstantLib;
import com.kalamansee.utils.ProjectUtil;


import java.util.ArrayList;


/*Created by arpitjoshi on 7/7/16.
 */
public class RedeemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public Context context;
    public RedeemItemClickListner redeemItemClickListner;
    String TAG = "RedeemAdapter";
    String firstDate = "";
    String lastDate = "";
    private ArrayList<MyCampaignList> myCampaignLists;


    public RedeemAdapter(Context context) {
        this.context = context;

    }


    public RedeemAdapter(Context context, ArrayList<MyCampaignList> myCampaignLists) {
        firstDate = "";
        lastDate = "";
        this.myCampaignLists = myCampaignLists;
        this.context = context;
        viewBinderHelper.setOpenOnlyOne(true);

     /*  this.redeemList = addHeader(this.redeemList);
    */
        notifyDataSetChanged();
    }

    public void setRedeemItemClickListner(RedeemItemClickListner redeemItemClickListner) {
        this.redeemItemClickListner = redeemItemClickListner;
    }

    public void setNewList(Context context, ArrayList<MyCampaignList> dataList) {
        firstDate = "";
        lastDate = "";
        this.context = context;
        this.myCampaignLists = dataList;
        viewBinderHelper.setOpenOnlyOne(true);

        notifyDataSetChanged();
      /*  this.redeemList = addHeader(this.redeemList);
  */
    }

    public ArrayList<MyCampaignList> getUpdatedMessageArraylIst() {
        return this.myCampaignLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root;
        switch (viewType) {
            case ConstantLib.GIFT_DETAIL:
                root = inflater.inflate(R.layout.redeem_list_items_gifts_details, parent, false);
                return new GiftDetailViewHolder(root, viewType);

            case ConstantLib.GIFT_COUPEN:
                root = inflater.inflate(R.layout.redeem_list_item_gift_coupen, parent, false);
                return new GiftCoupenViewHolder(root, viewType, context);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final MyCampaignList item = myCampaignLists.get(position);
        switch (viewHolder.getItemViewType()) {
            case ConstantLib.GIFT_DETAIL:
                final GiftDetailViewHolder giftDetailViewHolder = (GiftDetailViewHolder) viewHolder;
                giftDetailViewHolder.swipeRevealLayout.close(true);
                viewBinderHelper.bind(giftDetailViewHolder.swipeRevealLayout, item.getUnique_campaign_share_id());
                giftDetailViewHolder.detailRedeemItems.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redeemItemClickListner.onRedeemItemClick(position, v.getId(), myCampaignLists);
                    }
                });
                giftDetailViewHolder.delete_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redeemItemClickListner.onRedeemItemClick(position, v.getId(), myCampaignLists);
                    }
                });
                Glide.with(context).load(item.getVendor_logo_image_url()).
                        placeholder(R.drawable.place_holder_gallery)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(giftDetailViewHolder.vendorImage);
                if (item.getIs_reedemed_by_user().equalsIgnoreCase("1")) {
                    giftDetailViewHolder.expiryDateText.setText(context.getString(R.string.txt_redeemed));
                    giftDetailViewHolder.detailRedeemItems.setBackgroundColor(ContextCompat.getColor(context,R.color.redeem_delete_on));
                } else {

                    giftDetailViewHolder.expiryDateText.setText(context.getString(R.string.txt_expiry_date)+" "+ProjectUtil.formattedDateFromString(item.getEnd_date_time(),false));
                    giftDetailViewHolder.detailRedeemItems.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_redeem_item_selector));

                }
                giftDetailViewHolder.offerText.setText(item.getCampaign_name());
                giftDetailViewHolder.vendorName.setText(item.getVendor_name());

                break;

            case ConstantLib.GIFT_COUPEN:
                GiftCoupenViewHolder dataViewHolder = (GiftCoupenViewHolder) viewHolder;
                dataViewHolder.giftCoupenLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redeemItemClickListner.onRedeemItemClick(position, v.getId(), myCampaignLists);
                    }
                });
                dataViewHolder.giftSenderNameText.setText(myCampaignLists.get(position).getCampaign_owner_full_name());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (myCampaignLists.get(position).getIs_read().equalsIgnoreCase("0")) {
            return ConstantLib.GIFT_COUPEN;

        } else {
            return ConstantLib.GIFT_DETAIL;

        }
    }

    @Override
    public int getItemCount() {
        return myCampaignLists.size();
    }

    public interface RedeemItemClickListner {
        public void onRedeemItemClick(int postion, int id, ArrayList<MyCampaignList> myCampaignLists);
    }

    public static class GiftCoupenViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView giftSenderNameText;
        RelativeLayout giftCoupenLayout;

        public GiftCoupenViewHolder(View itemView, int viewType, Context context) {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-SemiboldItalic.ttf");
            giftSenderNameText = (AppCompatTextView) itemView.findViewById(R.id.gift_sender_text);
            giftCoupenLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_gift_coupen);
            giftSenderNameText.setTypeface(font);


        }


    }

    public static class GiftDetailViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout redeemGiftDetaillayout;
        ImageView vendorImage;
        AppCompatTextView vendorName, offerText, expiryDateText;
        FrameLayout delete_layout, detailRedeemItems;
        SwipeRevealLayout swipeRevealLayout;

        public GiftDetailViewHolder(View itemView, int viewType) {
            super(itemView);
            swipeRevealLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            detailRedeemItems = (FrameLayout) itemView.findViewById(R.id.detailRedeemItems);
            redeemGiftDetaillayout = (RelativeLayout) itemView.findViewById(R.id.relative_gift_detail_layout);
            vendorImage = (ImageView) itemView.findViewById(R.id.vendor_image_logo);
            vendorName = (AppCompatTextView) itemView.findViewById(R.id.vendorName);
            expiryDateText = (AppCompatTextView) itemView.findViewById(R.id.expiryDateText);
            offerText = (AppCompatTextView) itemView.findViewById(R.id.offerText);
            delete_layout = (FrameLayout) itemView.findViewById(R.id.deleteMyCampaign);

        }


    }
}


