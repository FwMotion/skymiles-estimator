const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const {BundleAnalyzerPlugin} = require('webpack-bundle-analyzer');
const Dotenv = require('dotenv-webpack');

const cssLoader = 'css-loader';


const postcssLoader = {
  loader: 'postcss-loader',
  options: {
    postcssOptions: {
      plugins: [
        require('tailwindcss')('tailwind.config.js'),
        'autoprefixer',
        ...(process.env.NODE_ENV === 'production' ? ['cssnano'] : [])
      ]
    }
  }
};

module.exports = function(env, { analyze }) {
  const production = env.production || process.env.NODE_ENV === 'production';
  return {
    target: 'web',
    mode: production ? 'production' : 'development',
    devtool: production ? undefined : 'eval-cheap-source-map',
    entry: {
      'skymiles-estimator': './src/main.js'
    },
    output: {
      path: path.resolve(__dirname, 'target', 'dist'),
      filename: production ? '[name].[contenthash].bundle.js' : '[name].bundle.js'
    },
    resolve: {
      extensions: ['.js'],
      modules: [path.resolve(__dirname, 'src'), path.resolve(__dirname, 'dev-app'), 'node_modules'],
      alias: production ? {
        'skymiles-estimator-calculator': path.resolve(__dirname, 'target/js/skymiles-estimator-calculator.js')
      } : {
        ...[
          'fetch-client',
          'kernel',
          'metadata',
          'platform',
          'platform-browser',
          'plugin-conventions',
          'route-recognizer',
          'router',
          'router-lite',
          'runtime',
          'runtime-html',
          'testing',
          'webpack-loader',
        ].reduce((map, pkg) => {
          const name = `@aurelia/${pkg}`;
          map[name] = path.resolve(__dirname, 'node_modules', name, 'dist/esm/index.dev.mjs');
          return map;
        }, {
          'aurelia': path.resolve(__dirname, 'node_modules/aurelia/dist/esm/index.dev.mjs'),
          'skymiles-estimator-calculator': path.resolve(__dirname, 'target/js/skymiles-estimator-calculator.js')
        })
      }
    },
    devServer: {
      historyApiFallback: true,
      open: !process.env.CI,
      port: 9000
    },
    module: {
      rules: [
        { test: /\.(png|svg|jpg|jpeg|gif)$/i, type: 'asset' },
        { test: /\.(woff|woff2|ttf|eot|svg|otf)(\?v=[0-9]\.[0-9]\.[0-9])?$/i,  type: 'asset' },
        { test: /\.css$/i, use: [ 'style-loader', cssLoader, postcssLoader ] },
        { test: /\.js$/i, use: ['babel-loader', '@aurelia/webpack-loader'], exclude: /node_modules/ },
        {
          test: /[/\\]src[/\\].+\.html$/i,
          use: '@aurelia/webpack-loader',
          exclude: /node_modules/
        }
      ]
    },
    plugins: [
      new HtmlWebpackPlugin({ template: 'index.html' }),
      new Dotenv({
        path: `./target/.env${production ? '' :  '.' + (process.env.NODE_ENV || 'development')}`,
      }),
      analyze && new BundleAnalyzerPlugin()
    ].filter(p => p)
  }
}
