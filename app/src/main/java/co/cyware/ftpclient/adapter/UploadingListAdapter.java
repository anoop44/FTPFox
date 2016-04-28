package co.cyware.ftpclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import co.cyware.ftpclient.R;
import co.cyware.ftpclient.service.ftp.FtpUploadItem;

/**
 * Created by Anoop S S on 28/4/16.
 */
public class UploadingListAdapter extends RecyclerView.Adapter<UploadingListAdapter.UploadingListItemHolder> {

    private static final int VIEW_TYPE_UPLOADING = 0;
    private static final int VIEW_TYPE_UPLOADED = 1;

    private List<FtpUploadItem> mUploadingFileList;

    @Override
    public UploadingListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uploading_list_item_layout, null);
        return new UploadingListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(UploadingListItemHolder holder, int position) {

        FtpUploadItem ftpUploadItem = mUploadingFileList.get(position);
        holder.mFileName.setText(ftpUploadItem.getRemoteFileName());

        switch (getItemViewType(position)) {
            case VIEW_TYPE_UPLOADED:
                holder.mFileUploadProgress.setVisibility(View.GONE);
                holder.mFileUploadCancelBtn.setVisibility(View.GONE);
                break;

            case VIEW_TYPE_UPLOADING:
                holder.mFileUploadProgress.setVisibility(View.VISIBLE);
                holder.mFileUploadCancelBtn.setVisibility(View.VISIBLE);
                holder.mFileUploadProgress.setProgress((int) (ftpUploadItem.getUploadedSize() * 100 / ftpUploadItem.getTotalSize()));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mUploadingFileList.get(position).isDownloaded() ? VIEW_TYPE_UPLOADED : VIEW_TYPE_UPLOADING;
    }

    public void setUploadFileList(List<FtpUploadItem> ftpUploadItems) {
        mUploadingFileList = ftpUploadItems;
        notifyDataSetChanged();
    }

    public List<FtpUploadItem> getList() {
        return mUploadingFileList;
    }

    @Override
    public int getItemCount() {
        return mUploadingFileList == null ? 0 : mUploadingFileList.size();
    }

    public static class UploadingListItemHolder extends RecyclerView.ViewHolder {

        protected TextView mFileName;
        protected ProgressBar mFileUploadProgress;
        protected Button mFileUploadCancelBtn;

        public UploadingListItemHolder(View itemView) {
            super(itemView);

            mFileName = (TextView) itemView.findViewById(R.id.uploading_file_name);
            mFileUploadCancelBtn = (Button) itemView.findViewById(R.id.uploading_file_cancel_btn);
            mFileUploadProgress = (ProgressBar) itemView.findViewById(R.id.uploading_file_progress);
            mFileUploadProgress.setMax(100);
        }
    }
}
