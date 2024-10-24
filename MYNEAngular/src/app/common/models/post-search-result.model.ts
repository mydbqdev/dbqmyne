export class PostSearchResult {
	postId: string;
	userId: string;
    creatorName:string;
    zipCode:string;
    description:string;
    likeCount:number;
    isLiked:boolean;
    commentsCount:number;
    createdAt:string;
    updatedAt:string;
    mediaDetails:MediaDetails[];
}

export class MediaDetails {
	contentType: string;
	type: string;
    url:string; 
}