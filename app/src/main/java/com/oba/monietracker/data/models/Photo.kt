package com.oba.monietracker.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "alt_description")
    val altDescription: String? = null,
    @Json(name = "alternative_slugs")
    val alternativeSlugs: AlternativeSlugs? = null,
    @Json(name = "asset_type")
    val assetType: String? = null,
    @Json(name = "blur_hash")
    val blurHash: String? = null,
    @Json(name = "breadcrumbs")
    val breadcrumbs: List<Breadcrumb?>? = null,
    @Json(name = "color")
    val color: String? = null,
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any?>? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "height")
    val height: Int? = null,
    @Json(name = "id")
    //@PrimaryKey
    val id: String? = null,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null,
    @Json(name = "likes")
    val likes: Int? = null,
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "promoted_at")
    val promotedAt: String? = null,
    @Json(name = "slug")
    val slug: String? = null,
    @Json(name = "sponsorship")
    val sponsorship: Any? = null,
    @Json(name = "tags")
    val tags: List<Tag?>? = null,
    @Json(name = "topic_submissions")
    val topicSubmissions: TopicSubmissions? = null,
    @Json(name = "updated_at")
    val updatedAt: String? = null,
    @Json(name = "urls")
    val urls: Urls? = null,
    @Json(name = "user")
    val user: User? = null,
    @Json(name = "width")
    val width: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class AlternativeSlugs(
        @Json(name = "de")
        val de: String?,
        @Json(name = "en")
        val en: String?,
        @Json(name = "es")
        val es: String?,
        @Json(name = "fr")
        val fr: String?,
        @Json(name = "it")
        val `it`: String?,
        @Json(name = "ja")
        val ja: String?,
        @Json(name = "ko")
        val ko: String?,
        @Json(name = "pt")
        val pt: String?
    )

    @JsonClass(generateAdapter = true)
    data class Breadcrumb(
        @Json(name = "index")
        val index: Int?,
        @Json(name = "slug")
        val slug: String?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "type")
        val type: String?
    )

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "download")
        val download: String?,
        @Json(name = "download_location")
        val downloadLocation: String?,
        @Json(name = "html")
        val html: String?,
        @Json(name = "self")
        val self: String?
    )

    @JsonClass(generateAdapter = true)
    data class Tag(
        @Json(name = "source")
        val source: Source?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "type")
        val type: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Source(
            @Json(name = "ancestry")
            val ancestry: Ancestry?,
            @Json(name = "cover_photo")
            val coverPhoto: CoverPhoto?,
            @Json(name = "description")
            val description: String?,
            @Json(name = "meta_description")
            val metaDescription: String?,
            @Json(name = "meta_title")
            val metaTitle: String?,
            @Json(name = "subtitle")
            val subtitle: String?,
            @Json(name = "title")
            val title: String?
        ) {
            @JsonClass(generateAdapter = true)
            data class Ancestry(
                @Json(name = "category")
                val category: Category?,
                @Json(name = "subcategory")
                val subcategory: Subcategory?,
                @Json(name = "type")
                val type: Type?
            ) {
                @JsonClass(generateAdapter = true)
                data class Category(
                    @Json(name = "pretty_slug")
                    val prettySlug: String?,
                    @Json(name = "slug")
                    val slug: String?
                )

                @JsonClass(generateAdapter = true)
                data class Subcategory(
                    @Json(name = "pretty_slug")
                    val prettySlug: String?,
                    @Json(name = "slug")
                    val slug: String?
                )

                @JsonClass(generateAdapter = true)
                data class Type(
                    @Json(name = "pretty_slug")
                    val prettySlug: String?,
                    @Json(name = "slug")
                    val slug: String?
                )
            }

            @JsonClass(generateAdapter = true)
            data class CoverPhoto(
                @Json(name = "alt_description")
                val altDescription: String?,
                @Json(name = "alternative_slugs")
                val alternativeSlugs: AlternativeSlugs?,
                @Json(name = "asset_type")
                val assetType: String?,
                @Json(name = "blur_hash")
                val blurHash: String?,
                @Json(name = "breadcrumbs")
                val breadcrumbs: List<Breadcrumb?>?,
                @Json(name = "color")
                val color: String?,
                @Json(name = "created_at")
                val createdAt: String?,
                @Json(name = "current_user_collections")
                val currentUserCollections: List<Any?>?,
                @Json(name = "description")
                val description: String?,
                @Json(name = "height")
                val height: Int?,
                @Json(name = "id")
                val id: String?,
                @Json(name = "liked_by_user")
                val likedByUser: Boolean?,
                @Json(name = "likes")
                val likes: Int?,
                @Json(name = "links")
                val links: Links?,
                @Json(name = "plus")
                val plus: Boolean?,
                @Json(name = "premium")
                val premium: Boolean?,
                @Json(name = "promoted_at")
                val promotedAt: String?,
                @Json(name = "slug")
                val slug: String?,
                @Json(name = "sponsorship")
                val sponsorship: Any?,
                @Json(name = "topic_submissions")
                val topicSubmissions: TopicSubmissions?,
                @Json(name = "updated_at")
                val updatedAt: String?,
                @Json(name = "urls")
                val urls: Urls?,
                @Json(name = "user")
                val user: User?,
                @Json(name = "width")
                val width: Int?
            ) {
                @JsonClass(generateAdapter = true)
                data class AlternativeSlugs(
                    @Json(name = "de")
                    val de: String?,
                    @Json(name = "en")
                    val en: String?,
                    @Json(name = "es")
                    val es: String?,
                    @Json(name = "fr")
                    val fr: String?,
                    @Json(name = "it")
                    val `it`: String?,
                    @Json(name = "ja")
                    val ja: String?,
                    @Json(name = "ko")
                    val ko: String?,
                    @Json(name = "pt")
                    val pt: String?
                )

                @JsonClass(generateAdapter = true)
                data class Breadcrumb(
                    @Json(name = "index")
                    val index: Int?,
                    @Json(name = "slug")
                    val slug: String?,
                    @Json(name = "title")
                    val title: String?,
                    @Json(name = "type")
                    val type: String?
                )

                @JsonClass(generateAdapter = true)
                data class Links(
                    @Json(name = "download")
                    val download: String?,
                    @Json(name = "download_location")
                    val downloadLocation: String?,
                    @Json(name = "html")
                    val html: String?,
                    @Json(name = "self")
                    val self: String?
                )

                @JsonClass(generateAdapter = true)
                data class TopicSubmissions(
                    @Json(name = "color-of-water")
                    val colorOfWater: ColorOfWater?,
                    @Json(name = "health")
                    val health: Health?,
                    @Json(name = "textures-patterns")
                    val texturesPatterns: TexturesPatterns?,
                    @Json(name = "wallpapers")
                    val wallpapers: Wallpapers?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class ColorOfWater(
                        @Json(name = "approved_on")
                        val approvedOn: String?,
                        @Json(name = "status")
                        val status: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class Health(
                        @Json(name = "approved_on")
                        val approvedOn: String?,
                        @Json(name = "status")
                        val status: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class TexturesPatterns(
                        @Json(name = "approved_on")
                        val approvedOn: String?,
                        @Json(name = "status")
                        val status: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class Wallpapers(
                        @Json(name = "approved_on")
                        val approvedOn: String?,
                        @Json(name = "status")
                        val status: String?
                    )
                }

                @JsonClass(generateAdapter = true)
                data class Urls(
                    @Json(name = "full")
                    val full: String?,
                    @Json(name = "raw")
                    val raw: String?,
                    @Json(name = "regular")
                    val regular: String?,
                    @Json(name = "small")
                    val small: String?,
                    @Json(name = "small_s3")
                    val smallS3: String?,
                    @Json(name = "thumb")
                    val thumb: String?
                )

                @JsonClass(generateAdapter = true)
                data class User(
                    @Json(name = "accepted_tos")
                    val acceptedTos: Boolean?,
                    @Json(name = "bio")
                    val bio: String?,
                    @Json(name = "first_name")
                    val firstName: String?,
                    @Json(name = "for_hire")
                    val forHire: Boolean?,
                    @Json(name = "id")
                    val id: String?,
                    @Json(name = "instagram_username")
                    val instagramUsername: String?,
                    @Json(name = "last_name")
                    val lastName: String?,
                    @Json(name = "links")
                    val links: Links?,
                    @Json(name = "location")
                    val location: String?,
                    @Json(name = "name")
                    val name: String?,
                    @Json(name = "portfolio_url")
                    val portfolioUrl: String?,
                    @Json(name = "profile_image")
                    val profileImage: ProfileImage?,
                    @Json(name = "social")
                    val social: Social?,
                    @Json(name = "total_collections")
                    val totalCollections: Int?,
                    @Json(name = "total_likes")
                    val totalLikes: Int?,
                    @Json(name = "total_photos")
                    val totalPhotos: Int?,
                    @Json(name = "total_promoted_photos")
                    val totalPromotedPhotos: Int?,
                    @Json(name = "twitter_username")
                    val twitterUsername: String?,
                    @Json(name = "updated_at")
                    val updatedAt: String?,
                    @Json(name = "username")
                    val username: String?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Links(
                        @Json(name = "followers")
                        val followers: String?,
                        @Json(name = "following")
                        val following: String?,
                        @Json(name = "html")
                        val html: String?,
                        @Json(name = "likes")
                        val likes: String?,
                        @Json(name = "photos")
                        val photos: String?,
                        @Json(name = "portfolio")
                        val portfolio: String?,
                        @Json(name = "self")
                        val self: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class ProfileImage(
                        @Json(name = "large")
                        val large: String?,
                        @Json(name = "medium")
                        val medium: String?,
                        @Json(name = "small")
                        val small: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class Social(
                        @Json(name = "instagram_username")
                        val instagramUsername: String?,
                        @Json(name = "paypal_email")
                        val paypalEmail: Any?,
                        @Json(name = "portfolio_url")
                        val portfolioUrl: String?,
                        @Json(name = "twitter_username")
                        val twitterUsername: String?
                    )
                }
            }
        }
    }

    @JsonClass(generateAdapter = true)
    data class TopicSubmissions(
        @Json(name = "business-work")
        val businessWork: BusinessWork?,
        @Json(name = "interiors")
        val interiors: Interiors?
    ) {
        @JsonClass(generateAdapter = true)
        data class BusinessWork(
            @Json(name = "approved_on")
            val approvedOn: String?,
            @Json(name = "status")
            val status: String?
        )

        @JsonClass(generateAdapter = true)
        data class Interiors(
            @Json(name = "status")
            val status: String?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Urls(
        @Json(name = "full")
        val full: String?,
        @Json(name = "raw")
        val raw: String?,
        @Json(name = "regular")
        val regular: String?,
        @Json(name = "small")
        val small: String?,
        @Json(name = "small_s3")
        val smallS3: String?,
        @Json(name = "thumb")
        val thumb: String?
    )

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "accepted_tos")
        val acceptedTos: Boolean?,
        @Json(name = "bio")
        val bio: String?,
        @Json(name = "first_name")
        val firstName: String?,
        @Json(name = "for_hire")
        val forHire: Boolean?,
        @Json(name = "id")
        val id: String?,
        @Json(name = "instagram_username")
        val instagramUsername: String?,
        @Json(name = "last_name")
        val lastName: String?,
        @Json(name = "links")
        val links: Links?,
        @Json(name = "location")
        val location: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "portfolio_url")
        val portfolioUrl: String?,
        @Json(name = "profile_image")
        val profileImage: ProfileImage?,
        @Json(name = "social")
        val social: Social?,
        @Json(name = "total_collections")
        val totalCollections: Int?,
        @Json(name = "total_likes")
        val totalLikes: Int?,
        @Json(name = "total_photos")
        val totalPhotos: Int?,
        @Json(name = "total_promoted_photos")
        val totalPromotedPhotos: Int?,
        @Json(name = "twitter_username")
        val twitterUsername: String?,
        @Json(name = "updated_at")
        val updatedAt: String?,
        @Json(name = "username")
        val username: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Links(
            @Json(name = "followers")
            val followers: String?,
            @Json(name = "following")
            val following: String?,
            @Json(name = "html")
            val html: String?,
            @Json(name = "likes")
            val likes: String?,
            @Json(name = "photos")
            val photos: String?,
            @Json(name = "portfolio")
            val portfolio: String?,
            @Json(name = "self")
            val self: String?
        )

        @JsonClass(generateAdapter = true)
        data class ProfileImage(
            @Json(name = "large")
            val large: String?,
            @Json(name = "medium")
            val medium: String?,
            @Json(name = "small")
            val small: String?
        )

        @JsonClass(generateAdapter = true)
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String?,
            @Json(name = "paypal_email")
            val paypalEmail: Any?,
            @Json(name = "portfolio_url")
            val portfolioUrl: String?,
            @Json(name = "twitter_username")
            val twitterUsername: String?
        )
    }
}