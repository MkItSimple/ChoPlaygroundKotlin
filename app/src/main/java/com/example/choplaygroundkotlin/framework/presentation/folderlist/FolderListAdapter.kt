package com.example.choplaygroundkotlin.framework.presentation.folderlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.recyclerview.widget.AsyncListDiffer
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.example.choplaygroundkotlin.R
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.framework.common.changeColor
import kotlinx.android.synthetic.main.layout_folder_list_item.view.*

class FolderListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedFolders: LiveData<ArrayList<Folder>>,
    private val dateUtil: DateUtil
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Folder>() {

        override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_folder_list_item,
                parent,
                false
            ),
            interaction,
            lifecycleOwner,
            selectedFolders,
            dateUtil
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FolderViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Folder>) {
        differ.submitList(list)
    }

    class FolderViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?,
        private val lifecycleOwner: LifecycleOwner,
        private val selectedFolders: LiveData<ArrayList<Folder>>,
        private val dateUtil: DateUtil
    ) : RecyclerView.ViewHolder(itemView) {

        private val COLOR_GREY = R.color.app_background_color
        private val COLOR_GREY_5 = R.color.grey5
        private val COLOR_PRIMARY = R.color.colorPrimary
        private lateinit var folder: Folder

        fun bind(item: Folder) = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            setOnLongClickListener {
                interaction?.activateMultiSelectionMode()
                interaction?.onItemSelected(adapterPosition, item)
                true
            }

            folder_name.text = item.folder_name

            selectedFolders.observe(lifecycleOwner, Observer { folders ->

                if(folders != null){
                    if(folders.contains(folder)){
                        changeColor(
                            newColor = COLOR_GREY_5
                        )
                    }
                    else{
                        changeColor(
                            newColor = COLOR_PRIMARY
                        )
                    }
                }else{
                    changeColor(
                        newColor = COLOR_PRIMARY
                    )
                }
            })
        }
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Folder)

        fun restoreListPosition()

        fun isMultiSelectionModeEnabled(): Boolean

        fun activateMultiSelectionMode()

        fun isFolderSelected(folder: Folder): Boolean
    }
}