package com.dmm.bootcamp.yatter2025.ui.yweetdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2025.R
import com.dmm.bootcamp.yatter2025.ui.theme.Yatter2025Theme
import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.YweetBindingModel

@Composable
fun YweetDetailTemplate(
    yweet: YweetBindingModel
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "ツイート詳細") })

        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current

            val placeholder = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.avatar_placeholder,
                null
            )
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(yweet.avatar)
                        .placeholder(placeholder)
                        .error(placeholder)
                        .fallback(placeholder)
                        .setHeader("User-Agent", "Mozilla/5.0")
                        .build(),
                    contentDescription = "アバター画像",
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(16.dp)
                )
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = yweet.displayName,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "@${yweet.username}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            Card(
                backgroundColor = Color.LightGray,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = yweet.content
                )
            }
            LazyRow {
                items(yweet.attachmentImageList) { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YweetDetailTemplatePreview() {
    Yatter2025Theme {
        Surface {
            YweetDetailTemplate(
                yweet = YweetBindingModel(
                    id = "id1",
                    displayName = "display name1",
                    username = "username1",
                    avatar = null,
                    content = "。すまり取け受を , , に数引、で数関elbasopmoCは\n" +
                            "。すまりあが要必\n" +
                            "るすを定設移遷面画のとご面画各てしを義定の ずま、はにるす現実を移遷面画で\n" +
                            "。すでい多がとこう使をesopmoc-noitagivanに移遷面画はでesopmoC kcapteJ\n" +
                            "。いさだくでいなしペピコはにトクェジロプrettaY、でのなドーコの\n" +
                            "用明説はらちこ。うょしまび学を法方の移遷面画のでesopmoC kcapteJずま、に前る入に装実線導の面画ンイグロ\n" +
                            "ていつに移遷面画たっ使をesopmoc-noitagivan\n" +
                            "。すまし更変を動挙の時たし動起をリプア\n" +
                            "。すまい行を装実線導の面画ンイラムイタクッリブパと面画ンイグロ\n" +
                            "装実線導",
                    attachmentImageList = listOf()
                )
            )
        }
    }
}