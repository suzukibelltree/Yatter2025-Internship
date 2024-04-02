package com.dmm.bootcamp.yatter2024.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.yatter2024Theme

@Composable
internal fun StatusRow(
  statusBindingModel: StatusBindingModel,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
//    AsyncImage(
//      modifier = Modifier.size(48.dp),
//      model = "https://avatars.githubusercontent.com/u/19385268?v=4",
//      contentDescription = "アバター画像",
//      contentScale = ContentScale.Crop
//    )

    Image(
      modifier = Modifier.size(48.dp),
      imageVector = Icons.Default.Person,
      contentDescription = "person"
    )

    Column {
      Row {
        Text(text = statusBindingModel.displayName)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
          Text(
            text = "@${statusBindingModel.username}"
          )
        }
      }
      Text(text = statusBindingModel.content)

      Row(modifier = Modifier.fillMaxWidth()) {
        statusBindingModel.attachmentMediaList.forEach {
          AsyncImage(
            modifier = Modifier.size(96.dp),
            model = it.url,
            contentDescription = it.description
          )
        }
      }
    }
  }
}

@Preview
@Composable
private fun StatusRowPreview() {
  yatter2024Theme {
    Surface {
      StatusRow(
        statusBindingModel = StatusBindingModel(
          id = "id",
          displayName = "mito",
          username = "mitohato14",
          avatar = "https://avatars.githubusercontent.com/u/19385268?v=4",
          content = "preview content",
          attachmentMediaList = emptyList()
        )
      )
    }
  }
}
