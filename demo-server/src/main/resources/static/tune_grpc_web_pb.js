/**
 * @fileoverview gRPC-Web generated client stub for demo
 * @enhanceable
 * @public
 */

// GENERATED CODE -- DO NOT EDIT!



const grpc = {};
grpc.web = require('grpc-web');

const proto = {};
proto.demo = require('./tune_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.demo.TuneServiceClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

  /**
   * @private @const {?Object} The credentials to be used to connect
   *    to the server
   */
  this.credentials_ = credentials;

  /**
   * @private @const {?Object} Options for the client
   */
  this.options_ = options;
};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.demo.TuneServicePromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

  /**
   * @private @const {?Object} The credentials to be used to connect
   *    to the server
   */
  this.credentials_ = credentials;

  /**
   * @private @const {?Object} Options for the client
   */
  this.options_ = options;
};


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.demo.MeasuredTuneRequest,
 *   !proto.demo.MeasuredTuneReply>}
 */
const methodInfo_TuneService_GetMeasuredTune = new grpc.web.AbstractClientBase.MethodInfo(
  proto.demo.MeasuredTuneReply,
  /** @param {!proto.demo.MeasuredTuneRequest} request */
  function(request) {
    return request.serializeBinary();
  },
  proto.demo.MeasuredTuneReply.deserializeBinary
);


/**
 * @param {!proto.demo.MeasuredTuneRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.demo.MeasuredTuneReply)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.demo.MeasuredTuneReply>|undefined}
 *     The XHR Node Readable Stream
 */
proto.demo.TuneServiceClient.prototype.getMeasuredTune =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/demo.TuneService/GetMeasuredTune',
      request,
      metadata || {},
      methodInfo_TuneService_GetMeasuredTune,
      callback);
};


/**
 * @param {!proto.demo.MeasuredTuneRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.demo.MeasuredTuneReply>}
 *     A native promise that resolves to the response
 */
proto.demo.TuneServicePromiseClient.prototype.getMeasuredTune =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/demo.TuneService/GetMeasuredTune',
      request,
      metadata || {},
      methodInfo_TuneService_GetMeasuredTune);
};


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.demo.MeasuredTuneRequest,
 *   !proto.demo.MeasuredTuneReply>}
 */
const methodInfo_TuneService_GetMeasuredTunes = new grpc.web.AbstractClientBase.MethodInfo(
  proto.demo.MeasuredTuneReply,
  /** @param {!proto.demo.MeasuredTuneRequest} request */
  function(request) {
    return request.serializeBinary();
  },
  proto.demo.MeasuredTuneReply.deserializeBinary
);


/**
 * @param {!proto.demo.MeasuredTuneRequest} request The request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.demo.MeasuredTuneReply>}
 *     The XHR Node Readable Stream
 */
proto.demo.TuneServiceClient.prototype.getMeasuredTunes =
    function(request, metadata) {
  return this.client_.serverStreaming(this.hostname_ +
      '/demo.TuneService/GetMeasuredTunes',
      request,
      metadata || {},
      methodInfo_TuneService_GetMeasuredTunes);
};


/**
 * @param {!proto.demo.MeasuredTuneRequest} request The request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.demo.MeasuredTuneReply>}
 *     The XHR Node Readable Stream
 */
proto.demo.TuneServicePromiseClient.prototype.getMeasuredTunes =
    function(request, metadata) {
  return this.client_.serverStreaming(this.hostname_ +
      '/demo.TuneService/GetMeasuredTunes',
      request,
      metadata || {},
      methodInfo_TuneService_GetMeasuredTunes);
};


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.demo.StandardDevRequest,
 *   !proto.demo.StandardDevReply>}
 */
const methodInfo_TuneService_GetStandardDev = new grpc.web.AbstractClientBase.MethodInfo(
  proto.demo.StandardDevReply,
  /** @param {!proto.demo.StandardDevRequest} request */
  function(request) {
    return request.serializeBinary();
  },
  proto.demo.StandardDevReply.deserializeBinary
);


/**
 * @param {!proto.demo.StandardDevRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.demo.StandardDevReply)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.demo.StandardDevReply>|undefined}
 *     The XHR Node Readable Stream
 */
proto.demo.TuneServiceClient.prototype.getStandardDev =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/demo.TuneService/GetStandardDev',
      request,
      metadata || {},
      methodInfo_TuneService_GetStandardDev,
      callback);
};


/**
 * @param {!proto.demo.StandardDevRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.demo.StandardDevReply>}
 *     A native promise that resolves to the response
 */
proto.demo.TuneServicePromiseClient.prototype.getStandardDev =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/demo.TuneService/GetStandardDev',
      request,
      metadata || {},
      methodInfo_TuneService_GetStandardDev);
};


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.demo.StandardDevRequest,
 *   !proto.demo.StandardDevReply>}
 */
const methodInfo_TuneService_SetStandardDev = new grpc.web.AbstractClientBase.MethodInfo(
  proto.demo.StandardDevReply,
  /** @param {!proto.demo.StandardDevRequest} request */
  function(request) {
    return request.serializeBinary();
  },
  proto.demo.StandardDevReply.deserializeBinary
);


/**
 * @param {!proto.demo.StandardDevRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.demo.StandardDevReply)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.demo.StandardDevReply>|undefined}
 *     The XHR Node Readable Stream
 */
proto.demo.TuneServiceClient.prototype.setStandardDev =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/demo.TuneService/SetStandardDev',
      request,
      metadata || {},
      methodInfo_TuneService_SetStandardDev,
      callback);
};


/**
 * @param {!proto.demo.StandardDevRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.demo.StandardDevReply>}
 *     A native promise that resolves to the response
 */
proto.demo.TuneServicePromiseClient.prototype.setStandardDev =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/demo.TuneService/SetStandardDev',
      request,
      metadata || {},
      methodInfo_TuneService_SetStandardDev);
};


module.exports = proto.demo;

